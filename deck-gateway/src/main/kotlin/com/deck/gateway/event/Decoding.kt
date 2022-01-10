package com.deck.gateway.event

import com.deck.gateway.util.Event
import com.deck.gateway.util.TeamXpAddEvent
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class GenericEvent(val type: String)

private val forgivingJson = Json { ignoreUnknownKeys = true }

interface EventDecoder {
    fun decodeEventFromJson(json: String): Event?

    fun decodeJsonFromPayload(payload: String): String?
}

class DefaultEventDecoder: EventDecoder {
    override fun decodeEventFromJson(json: String): Event? {
        val genericEvent = forgivingJson.decodeFromStringOrNull<GenericEvent>(json) ?: return null
        return when (genericEvent.type) {
            "TeamXpAdded" -> forgivingJson.decodeFromString<TeamXpAddEvent>(json)
            else -> println("Unsupported Event Received: $json").let { null }
        }
    }

    override fun decodeJsonFromPayload(payload: String): String? =
        payload.substringAfter(',').substringBeforeLast(']').let { if (!it.contains('{')) null else it }
}

inline fun <reified T> Json.decodeFromStringOrNull(string: String) = kotlin.runCatching { decodeFromString<T>(string) }.getOrNull()