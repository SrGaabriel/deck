package io.github.deck.common.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

public typealias GenericId = String
public typealias IntGenericId = Int
public typealias LongGenericId = Long

public object UUIDSerializer: KSerializer<UUID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("java.util.UUID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): UUID {
        return UUIDDeserializer.deserialize(decoder.decodeString())
    }
}

/**
 * From Jackson (https://github.com/FasterXML/jackson-databind/blob/2.14/src/main/java/com/fasterxml/jackson/databind/deser/std/UUIDDeserializer.java)
 * with modifications
 */
private object UUIDDeserializer {
    private val HEX_DIGITS = IntArray(127)

    init {
        Arrays.fill(HEX_DIGITS, -1)
        for (i in 0..9) {
            HEX_DIGITS['0'.code + i] = i
        }
        for (i in 0..5) {
            HEX_DIGITS['a'.code + i] = 10 + i
            HEX_DIGITS['A'.code + i] = 10 + i
        }
    }

    fun deserialize(id: String): UUID {
        if (id[8] != '-' || id[13] != '-' || id[18] != '-' || id[23] != '-') {
            throwBadFormatSerializationException(id)
        }
        var l1 = intFromChars(id, 0).toLong()
        l1 = l1 shl 32
        var l2 = shortFromChars(id, 9).toLong() shl 16
        l2 = l2 or shortFromChars(id, 14).toLong()
        val hi = l1 + l2
        val i1 = shortFromChars(id, 19) shl 16 or shortFromChars(id, 24)
        l1 = i1.toLong()
        l1 = l1 shl 32
        l2 = intFromChars(id, 28).toLong()
        l2 = l2 shl 32 ushr 32 // sign removal, Java-style. Ugh.
        val lo = l1 or l2
        return UUID(hi, lo)
    }

    private fun intFromChars(str: String, index: Int): Int {
        return ((byteFromChars(str, index) shl 24)
                + (byteFromChars(str, index + 2) shl 16)
                + (byteFromChars(str, index + 4) shl 8)
                + byteFromChars(str, index + 6))
    }

    private fun shortFromChars(str: String, index: Int): Int {
        return (byteFromChars(str, index) shl 8) + byteFromChars(str, index + 2)
    }

    private fun byteFromChars(str: String, index: Int): Int {
        val c1 = str[index]
        val c2 = str[index + 1]
        if (c1.code <= 127 && c2.code <= 127) {
            val hex = HEX_DIGITS[c1.code] shl 4 or HEX_DIGITS[c2.code]
            if (hex >= 0) {
                return hex
            }
        }
        if (c1.code > 127 || HEX_DIGITS[c1.code] < 0) {
            throwBadCharSerializationException(str, index, c1)
        } else throwBadCharSerializationException(str, index + 1, c2)
    }

    private fun throwBadCharSerializationException(uuidAsString: String?, index: Int, invalidChar: Char): Nothing =
        throw SerializationException(
            "Non-hex character '$invalidChar' at position $index not valid for UUID deserialization of $uuidAsString"
        )

    private fun throwBadFormatSerializationException(uuidAsString: String): Nothing =
        throw SerializationException("Invalid UUID format: $uuidAsString")
}