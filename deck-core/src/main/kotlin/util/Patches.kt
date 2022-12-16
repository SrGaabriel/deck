package io.github.srgaabriel.deck.core.util

import io.github.srgaabriel.deck.common.util.OptionalProperty
import io.github.srgaabriel.deck.common.util.nullableOptional
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = Patch.Serializer::class)
public sealed interface Patch<out T> {
    /** Not specified value, stays the same */
    public object Unchanged: Patch<Nothing>

    /** Value was specified, but as null (could be removed/reset) */
    public object Null: Patch<Nothing>

    /**
     *  Old value was changed to [value]
     *
     * @param value new value
     */
    public data class New<T>(val value: T): Patch<T>

    public class Serializer<T>(private val valueSerializer: KSerializer<T>): KSerializer<Patch<T>> {
        override val descriptor: SerialDescriptor = valueSerializer.descriptor

        @OptIn(ExperimentalSerializationApi::class)
        override fun deserialize(decoder: Decoder): Patch<T> = when {
            decoder.decodeNotNullMark() -> Null
            else -> New(decoder.decodeSerializableValue(valueSerializer))
        }

        @OptIn(ExperimentalSerializationApi::class)
        override fun serialize(encoder: Encoder, value: Patch<T>): Unit = when (value) {
            is Unchanged -> throw SerializationException("Tried to serialize an optional property that had no value present.")
            is Null -> encoder.encodeNull()
            is New<T> -> encoder.encodeSerializableValue(valueSerializer, value.value)
        }
    }
}

/**
 * Returns null if this patch is [Patch.Unchanged] or [Patch.Null],
 * otherwise it just returns [Patch.New].
 *
 * @return null if this is not a [Patch.New] patch, otherwise [Patch.New.value]
 */
public fun <T> Patch<T>.asNullable(): T? = when (this) {
    is Patch.New<T> -> value
    else -> null
}

/**
 * Transforms the specified [OptionalProperty] into a [Patch] type.
 *
 * @return optional as a patch
 */
public fun <T> OptionalProperty<T>.asPatch(): Patch<T> = when (this) {
    is OptionalProperty.NotPresent -> Patch.Unchanged
    is OptionalProperty.Present -> {
        if (value == null) Patch.Null else Patch.New(value)
    }
}

/**
 * Transforms the specified [Patch] into an [OptionalProperty] type.
 *
 * This gives a [OptionalProperty.Present] type with **null** value in case this
 * patch is [Patch.Null]. If you do not want that, try [asNonNullableOptional].
 *
 * @return patch as an optional
 */
public fun <T> Patch<T>.asOptional(): OptionalProperty<T?> = when (this) {
    is Patch.New<T> -> OptionalProperty.Present(value)
    is Patch.Null -> OptionalProperty.Present(null)
    is Patch.Unchanged -> OptionalProperty.NotPresent
}

/**
 * Transforms the specified [Patch] into an [OptionalProperty] type.
 *
 * This gives a [OptionalProperty.Present] with non-nullable type.
 * If this patch is [Patch.Null], it'll return a [OptionalProperty.NotPresent]. If you do not want that, try [asOptional].
 *
 * @return patch as an optional
 */
public fun <T> Patch<T>.asNonNullableOptional(): OptionalProperty<T> =
    asNullable().nullableOptional()