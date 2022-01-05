package com.guildedkt.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class IntSerializationStrategy<E: Enum<E>>(values: Map<Int, E>)
    : IdentifiableEnumSerializationStrategy<E, Int>(values)

class StringSerializationStrategy<E: Enum<E>>(values: Map<String, E>)
    : IdentifiableEnumSerializationStrategy<E, String>(values)

open class IdentifiableEnumSerializationStrategy<E: Enum<E>, ID>(private val values: Map<ID, E>) {
    fun getValueByIdentifier(id: ID): E = values[id] ?: throw SerializationException("Received value is not registered in wrapper enum.")

    fun getIdentifierFromValue(value: E): ID = values.filter { it.value == value }.firstNotNullOf { it.key }
}

open class StringIdEnumSerializer<T: Enum<T>>(val strategy: IdentifiableEnumSerializationStrategy<T, String>): KSerializer<T> {
    override fun deserialize(decoder: Decoder): T =
        strategy.getValueByIdentifier(decoder.decodeString())

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("EnumSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: T) =
        encoder.encodeString(strategy.getIdentifierFromValue(value))
}

open class IntIdEnumSerializer<T: Enum<T>>(val strategy: IdentifiableEnumSerializationStrategy<T, Int>): KSerializer<T> {
    override fun deserialize(decoder: Decoder): T =
        strategy.getValueByIdentifier(decoder.decodeInt())

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("EnumSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: T) =
        encoder.encodeInt(strategy.getIdentifierFromValue(value))
}
