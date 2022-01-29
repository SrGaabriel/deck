@file:OptIn(DeckExperimental::class)

package com.deck.gateway.event

import com.deck.common.util.DeckExperimental
import com.deck.gateway.event.type.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
public abstract class GatewayEvent {
    public open var gatewayId: Int = -1
}

private val polymorphicJson by lazy {
    Json {
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            polymorphic(GatewayEvent::class) {
                subclass(GatewayHelloEvent::class)
                subclass(GatewayServerXpAddedEvent::class)
                subclass(GatewayTeamMemberUpdatedEvent::class)
                subclass(GatewayChatMessageCreatedEvent::class)
                subclass(GatewayChatMessageUpdatedEvent::class)
                subclass(GatewayChatMessageDeletedEvent::class)
            }
        }
    }
}

public interface EventDecoder {
    public fun decodeEventFromPayload(payload: Payload): GatewayEvent?

    public fun decodeDataFromPayload(json: String): Payload
}

public class DefaultEventDecoder(private val gatewayId: Int) : EventDecoder {
    override fun decodeEventFromPayload(payload: Payload): GatewayEvent? = runCatching {
        return run {
            val newJsonObject = JsonObject(payload.data.toMutableMap().apply {
                put("type", JsonPrimitive(payload.type))
            })
            polymorphicJson.decodeFromJsonElement<GatewayEvent>(newJsonObject)
        }.also {
            it.gatewayId = gatewayId
        }
    }.onFailure { it.printStackTrace() }.getOrNull()

    override fun decodeDataFromPayload(json: String): Payload {
        val jsonObject = polymorphicJson.parseToJsonElement(json).jsonObject
        val eventType = jsonObject["t"]?.jsonPrimitive?.content ?: "HelloEvent"
        val eventData = jsonObject["d"]?.jsonObject ?: error("Received event without data")
        return Payload(
            type = eventType,
            data = eventData
        )
    }
}

public data class Payload(val type: String, val data: JsonObject)