package com.deck.common.util

import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

typealias GenericId = String
typealias IntGenericId = Int
typealias LongGenericId = Long

typealias Dictionary<K, V> = Map<K, V>

@Serializable(UniqueIdSerializer::class)
data class UniqueId(val raw: String)

@Serializable(with = TimestampSerializer::class)
data class Timestamp(
    val iso: String
) {
    @Transient
    val instant: Instant = Instant.parse(iso)
    @Transient
    val mills: Long = instant.toEpochMilliseconds()
}

object TimestampSerializer: KSerializer<Timestamp> {
    override fun deserialize(decoder: Decoder): Timestamp =
        Timestamp(decoder.decodeString())

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("TimestampSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Timestamp) =
        encoder.encodeString(value.iso)
}

object UniqueIdSerializer: KSerializer<UniqueId> {
    override fun deserialize(decoder: Decoder): UniqueId =
        UniqueId(decoder.decodeString())

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("UniqueIdSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UniqueId) =
        encoder.encodeString(value.raw)
}