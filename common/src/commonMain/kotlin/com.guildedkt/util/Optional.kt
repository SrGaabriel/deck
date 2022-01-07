package com.guildedkt.util

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
sealed class OptionalProperty<out T> {
    object NotPresent : OptionalProperty<Nothing>()
    data class Present<T>(val value: T) : OptionalProperty<T>()
}

open class OptionalPropertySerializer<T>(
    private val valueSerializer: KSerializer<T>
) : KSerializer<OptionalProperty<T>> {
    final override val descriptor: SerialDescriptor = valueSerializer.descriptor

    final override fun deserialize(decoder: Decoder): OptionalProperty<T> =
        OptionalProperty.Present(valueSerializer.deserialize(decoder))

    final override fun serialize(encoder: Encoder, value: OptionalProperty<T>) {
        when (value) {
            OptionalProperty.NotPresent -> throw SerializationException(
                "Tried to serialize an optional property that had no value present." +
                        " Is encodeDefaults false?"
            )
            is OptionalProperty.Present ->
                valueSerializer.serialize(encoder, value.value)
        }
    }
}

fun <T> T.optional() = OptionalProperty.Present(this)