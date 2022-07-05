package io.github.deck.gateway.event

import io.github.deck.gateway.event.type.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
public abstract class GatewayEvent {
    internal var _type: String? = null
    public val type: String get() = _type ?: error("Tried to infer event's type before it has been recognized")
    internal var _gatewayId: Int? = null
    public val gatewayId: Int get() = _gatewayId ?: error("Tried to access event's gateway id before it has been fired")
}

private val polymorphicJson by lazy {
    Json {
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            polymorphic(GatewayEvent::class) {
                subclass(GatewayHelloEvent::class)
                subclass(GatewayTeamMemberJoinedEvent::class)
                subclass(GatewayTeamMemberUpdatedEvent::class)
                subclass(GatewayTeamMemberRemovedEvent::class)
                subclass(GatewayTeamMemberBannedEvent::class)
                subclass(GatewayTeamMemberUnbannedEvent::class)
                subclass(GatewayChatMessageCreatedEvent::class)
                subclass(GatewayChatMessageUpdatedEvent::class)
                subclass(GatewayChatMessageDeletedEvent::class)
                subclass(GatewayServerRolesUpdatedEvent::class)
                subclass(GatewayServerWebhookCreatedEvent::class)
                subclass(GatewayServerWebhookUpdatedEvent::class)
                subclass(GatewayListItemCompletedEvent::class)
                subclass(GatewayListItemCreatedEvent::class)
                subclass(GatewayListItemUpdatedEvent::class)
                subclass(GatewayListItemDeletedEvent::class)
                subclass(GatewayListItemUncompletedEvent::class)
                subclass(GatewayDocumentationCreatedEvent::class)
                subclass(GatewayDocumentationUpdatedEvent::class)
                subclass(GatewayDocumentationDeletedEvent::class)
                subclass(GatewayServerChannelCreatedEvent::class)
                subclass(GatewayServerChannelDeletedEvent::class)
                subclass(GatewayChatMessageReactionCreatedEvent::class)
                subclass(GatewayChatMessageReactionDeletedEvent::class)
                subclass(GatewayCalendarEventCreatedEvent::class)
                subclass(GatewayCalendarEventUpdatedEvent::class)
                subclass(GatewayCalendarEventDeletedEvent::class)
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
            it._type = payload.type
            it._gatewayId = gatewayId
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