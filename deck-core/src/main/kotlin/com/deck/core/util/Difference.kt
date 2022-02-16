package com.deck.core.util

import com.deck.common.util.OptionalProperty
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A more formal class to represent patches/differences
 * between values.
 */
@Serializable(Difference.Serializer::class)
public sealed class Difference<out T> {
    /**
     * Value was not specified, so it stays the same.
     */
    public object Unchanged: Difference<Nothing>()

    /**
     * Value was specified to be null, in other words, was resetted/removed.
     */
    public object Null: Difference<Nothing>()

    /**
     * Value was changed to the new [value].
     *
     * @param value non-nullable specified value
     */
    public data class Value<T>(val value: T): Difference<T>()

    override fun toString(): String = when(this) {
        is Unchanged -> "Unchanged"
        is Null -> "Reset"
        is Value<*> -> value.toString()
    }

    public class Serializer<T>(private val valueSerializer: KSerializer<T>): KSerializer<Difference<T>> {
        override val descriptor: SerialDescriptor = valueSerializer.descriptor

        @OptIn(ExperimentalSerializationApi::class)
        override fun deserialize(decoder: Decoder): Difference<T> = when {
            decoder.decodeNotNullMark() -> Null
            else -> Value(decoder.decodeSerializableValue(valueSerializer))
        }

        @OptIn(ExperimentalSerializationApi::class)
        override fun serialize(encoder: Encoder, value: Difference<T>): Unit = when (value) {
            is Unchanged -> throw SerializationException("Tried to serialize an optional property that had no value present.")
            is Null -> encoder.encodeNull()
            is Value<T> -> encoder.encodeSerializableValue(valueSerializer, value.value)
        }
    }
}

/**
 * Transforms the specified [OptionalProperty] into a [Difference] type.
 *
 * @return optional as a difference
 */
public fun <T> OptionalProperty<T?>.asDifference(): Difference<T> = when(this) {
    is OptionalProperty.NotPresent -> Difference.Unchanged
    is OptionalProperty.Present -> {
        if (value == null) Difference.Null else Difference.Value(value!!)
    }
}

/**
 * Uses the specified [value] if the difference is either null
 * or missing. Does not support nulls.
 *
 * @see nullableOr
 * @param value value to replace in case of missing difference/null type.
 *
 * @return Difference value if present or the specified [value]
 */
public infix fun <T : Any> Difference<T>.or(value: T): T = when (this) {
    is Difference.Unchanged -> value
    else -> value
}

/**
 * Uses the specified [value]  if the difference is missing.
 *
 * @param value the value to replace the difference in casing of missing difference.
 *
 * @return Difference value if present, null in case of null new value, and the specified [value] itself in casing of missing difference.
 */
public infix fun <T> Difference<T>.nullableOr(value: T?): T? = when (this) {
    is Difference.Value<T> -> this.value
    is Difference.Null -> null
    else -> value
}

/**
 * Returns null if the difference is missing or already null,
 * otherwise returns the new value.
 */
public fun <T> Difference<T>.asNullable(): T? = when (this) {
    is Difference.Value<T> -> value
    else -> null
}