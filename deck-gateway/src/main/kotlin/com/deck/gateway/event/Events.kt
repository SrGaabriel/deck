package com.deck.gateway.event

import com.deck.gateway.event.type.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

abstract class GatewayEvent {
    var gatewayId: Int = 0
}

data class Payload(val type: String, val json: String) {
    val isValid = type != json && json.contains("{")
}

private val forgivingJson = Json { ignoreUnknownKeys = true }

interface EventDecoder {
    fun decodeEventFromPayload(payload: Payload): GatewayEvent?

    fun decodePayloadFromString(string: String): Payload?
}

class DefaultEventDecoder(val gatewayId: Int): EventDecoder {
    override fun decodeEventFromPayload(payload: Payload): GatewayEvent? = runCatching { when (payload.type) {
        "Hello" -> forgivingJson.decodeFromString<GatewayHelloEvent>(payload.json)
        "TeamXpAdded" -> forgivingJson.decodeFromString<GatewayTeamXpAddedEvent>(payload.json)
        "ChatChannelTyping" -> forgivingJson.decodeFromString<GatewayChannelTypingEvent>(payload.json)
        "ChatMessageCreated" -> forgivingJson.decodeFromString<GatewayChatMessageCreateEvent>(payload.json)
        "ChatMessageDeleted" -> forgivingJson.decodeFromString<GatewayChatMessageDeleteEvent>(payload.json)
        "TeamChannelCreated" -> forgivingJson.decodeFromString<GatewayTeamChannelCreated>(payload.json)
        "TeamChannelDeleted" -> forgivingJson.decodeFromString<GatewayTeamChannelDeleted>(payload.json)
        else -> println("Unsupported Event Received: ${payload.json}").let { null }
    }}.onFailure { it.printStackTrace() }.getOrNull()?.also {
        // TODO: Workaround, fix
        it.gatewayId = this.gatewayId
    }

    override fun decodePayloadFromString(string: String): Payload? {
        if (string.startsWith("0{")) // Handle payload event (structure different from the others)
            return Payload("Hello", string.substring(1))
        val payload = Payload(
            type = string.substringAfter('"').substringBefore('"'),
            json = string.substringAfter(',').substringBeforeLast(']')
        )
        return if (payload.isValid) payload else null
    }
}