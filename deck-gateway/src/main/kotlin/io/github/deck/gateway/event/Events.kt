package io.github.deck.gateway.event

import io.github.deck.gateway.event.type.*
import io.github.deck.gateway.util.GatewayOpcode
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
public abstract class GatewayEvent {
    @Transient
    internal var _payload: Payload? = null
    public val payload: Payload get() = _payload ?: error("Tried to access event's payload before it has been fired")
}

private val polymorphicJson by lazy {
    Json {
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            polymorphic(GatewayEvent::class) {
                subclass(GatewayHelloEvent::class)
                subclass(GatewayResumeEvent::class)
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

    public fun decodePayloadFromData(json: String): Payload
}

public class DefaultEventDecoder(private val gatewayId: Int) : EventDecoder {
    override fun decodeEventFromPayload(payload: Payload): GatewayEvent? = runCatching {
        return run {
            val newJsonObject = JsonObject(payload.data.toMutableMap().apply {
                put("type", JsonPrimitive(payload.type))
            })
            polymorphicJson.decodeFromJsonElement<GatewayEvent>(newJsonObject)
        }.also {
            it._payload = payload
        }
    }.onFailure { it.printStackTrace() }.getOrNull()

    override fun decodePayloadFromData(json: String): Payload {
        val jsonObject = polymorphicJson.parseToJsonElement(json).jsonObject
        val eventOpcode = jsonObject["op"]?.jsonPrimitive?.intOrNull ?: error("Invalid event opcode")
        val eventData = jsonObject["d"]?.jsonObject ?: error("Received event without data")
        val eventMessageId = if (eventOpcode == 0) jsonObject["s"]?.jsonPrimitive?.content else null
        val eventType = jsonObject["t"]?.jsonPrimitive?.content ?: when (eventOpcode) {
            GatewayOpcode.Dispatch -> error("Invalid event type")
            GatewayOpcode.Hello -> "HelloEvent"
            GatewayOpcode.Resume -> "ResumeEvent"
            else -> error("Unknown opcode $eventOpcode")
        }
        return Payload(
            type = eventType,
            opcode = eventOpcode,
            gatewayId = gatewayId,
            messageId = eventMessageId,
            data = eventData,
        )
    }
}

public data class Payload(
    val type: String,
    val opcode: Int,
    val gatewayId: Int,
    val messageId: String?,
    val data: JsonObject
)