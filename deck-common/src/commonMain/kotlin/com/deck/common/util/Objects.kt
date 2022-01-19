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

public typealias GenericId = String
public typealias IntGenericId = Int
public typealias LongGenericId = Long

public typealias Dictionary<K, V> = Map<K, V>

@Serializable(UniqueIdSerializer::class)
public data class UniqueId(val raw: String) {
    override fun toString(): String = raw
}

@Serializable(with = TimestampSerializer::class)
public data class Timestamp(
    val iso: String
) {
    @Transient
    val instant: Instant = Instant.parse(iso)

    @Transient
    val mills: Long = instant.toEpochMilliseconds()
}

public object TimestampSerializer : KSerializer<Timestamp> {
    override fun deserialize(decoder: Decoder): Timestamp =
        Timestamp(decoder.decodeString())

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("TimestampSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Timestamp): Unit =
        encoder.encodeString(value.iso)
}

public object UniqueIdSerializer : KSerializer<UniqueId> {
    override fun deserialize(decoder: Decoder): UniqueId =
        UniqueId(decoder.decodeString())

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("UniqueIdSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UniqueId): Unit =
        encoder.encodeString(value.raw)
}
