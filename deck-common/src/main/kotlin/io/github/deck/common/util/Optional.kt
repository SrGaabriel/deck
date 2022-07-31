package io.github.deck.common.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * https://medium.com/livefront/kotlinx-serialization-de-serializing-jsons-nullable-optional-properties-442c7f0c2614
 */
@Serializable(OptionalPropertySerializer::class)
public sealed class OptionalProperty<out T> {
    public object NotPresent : OptionalProperty<Nothing>()
    public data class Present<T>(val value: T) : OptionalProperty<T>() {
        override fun toString(): String = value.toString()
    }
}

public open class OptionalPropertySerializer<T>(
    private val valueSerializer: KSerializer<T>
) : KSerializer<OptionalProperty<T>> {
    final override val descriptor: SerialDescriptor = valueSerializer.descriptor

    final override fun deserialize(decoder: Decoder): OptionalProperty<T> =
        OptionalProperty.Present(valueSerializer.deserialize(decoder))

    final override fun serialize(encoder: Encoder, value: OptionalProperty<T>) {
        when (value) {
            OptionalProperty.NotPresent -> throw SerializationException(
                "Tried to serialize an optional property that had no value present."
            )
            is OptionalProperty.Present ->
                valueSerializer.serialize(encoder, value.value)
        }
    }
}

public fun <T> OptionalProperty<T>.getValue(): T =
    (this as? OptionalProperty.Present<T>)?.value ?: error("Tried to forcibly get the value of an absent optional property")

public fun <T> OptionalProperty<T>.isPresent(): Boolean =
    this is OptionalProperty.Present<T>

public fun <T> OptionalProperty<T>.isAbsent(): Boolean = !isPresent()

public fun <T> T.optional(): OptionalProperty.Present<T> = OptionalProperty.Present(this)

public fun <T> T?.nullableOptional(): OptionalProperty<T> =
    if (this == null) OptionalProperty.NotPresent else OptionalProperty.Present(this)

public fun <T> OptionalProperty<T>.asNullable(): T? = when (this) {
    is OptionalProperty.NotPresent -> null
    is OptionalProperty.Present<T> -> value
}

public fun <T, F> OptionalProperty<T>.map(block: (T) -> F): OptionalProperty<F> = when (this) {
    is OptionalProperty.NotPresent -> OptionalProperty.NotPresent
    is OptionalProperty.Present<T> -> block(this.value).optional()
}

public fun <T, F : Any> OptionalProperty<T>.mapNotNull(block: (T) -> F?): OptionalProperty<F> = when (this) {
    is OptionalProperty.NotPresent -> OptionalProperty.NotPresent
    is OptionalProperty.Present<T> -> block(value)?.optional() ?: OptionalProperty.NotPresent
}

public fun <T, F : Any> OptionalProperty<T?>.mapValueNotNull(block: (T) -> F): OptionalProperty<F> = when (this) {
    is OptionalProperty.NotPresent -> OptionalProperty.NotPresent
    is OptionalProperty.Present<T?> -> if (value == null) OptionalProperty.NotPresent else block(value).optional()
}

public fun <T> OptionalProperty<List<T>>.orEmpty(): List<T> =
    asNullable().orEmpty()