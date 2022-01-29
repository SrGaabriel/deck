package com.deck.common.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public typealias GenericId = String
public typealias IntGenericId = Int
public typealias LongGenericId = Long

@Serializable(UniqueId.Serializer::class)
public data class UniqueId(val raw: String) {
    override fun toString(): String = raw

    public companion object Serializer: KSerializer<UniqueId> {
        override fun deserialize(decoder: Decoder): UniqueId =
            UniqueId(decoder.decodeString())

        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("UniqueIdSerializer", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: UniqueId): Unit =
            encoder.encodeString(value.raw)
    }
}