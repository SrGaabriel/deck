package io.github.srgaabriel.deck.gateway.event

import io.github.srgaabriel.deck.common.log.DeckLogger
import io.github.srgaabriel.deck.common.util.OptionalProperty
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.gateway.event.type.*
import io.github.srgaabriel.deck.gateway.util.GatewayOpcode
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
public abstract class GatewayEvent {
    @Transient
    internal var _info: EventInfo? = null
    public val info: EventInfo get() = _info ?: error("Tried to access event's information before it was fired")

}

private val polymorphicJson by lazy {
    Json {
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            polymorphic(GatewayEvent::class) {
                subclass(GatewayHelloEvent::class)
                subclass(GatewayResumeEvent::class)
                subclass(GatewayServerMemberJoinedEvent::class)
                subclass(GatewayServerMemberUpdatedEvent::class)
                subclass(GatewayServerMemberRemovedEvent::class)
                subclass(GatewayServerMemberBannedEvent::class)
                subclass(GatewayServerMemberUnbannedEvent::class)
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
                subclass(GatewayServerChannelUpdatedEvent::class)
                subclass(GatewayServerChannelDeletedEvent::class)
                subclass(GatewayChatMessageReactionCreatedEvent::class)
                subclass(GatewayChatMessageReactionDeletedEvent::class)
                subclass(GatewayCalendarEventCreatedEvent::class)
                subclass(GatewayCalendarEventUpdatedEvent::class)
                subclass(GatewayCalendarEventDeletedEvent::class)
                subclass(GatewayCalendarEventRsvpUpdatedEvent::class)
                subclass(GatewayCalendarEventRsvpManyUpdatedEvent::class)
                subclass(GatewayCalendarEventRsvpDeletedEvent::class)
                subclass(GatewayForumTopicCreatedEvent::class)
                subclass(GatewayForumTopicUpdatedEvent::class)
                subclass(GatewayForumTopicDeletedEvent::class)
                subclass(GatewayForumTopicPinnedEvent::class)
                subclass(GatewayForumTopicUnpinnedEvent::class)
                subclass(GatewayForumTopicLockedEvent::class)
                subclass(GatewayForumTopicUnlockedEvent::class)
                subclass(GatewayForumTopicCommentCreatedEvent::class)
                subclass(GatewayForumTopicCommentUpdatedEvent::class)
                subclass(GatewayForumTopicCommentDeletedEvent::class)
                subclass(GatewayBotServerMembershipCreatedEvent::class)
                subclass(GatewayBotServerMembershipDeletedEvent::class)
                subclass(GatewayForumTopicReactionCreatedEvent::class)
                subclass(GatewayForumTopicReactionDeletedEvent::class)
                subclass(GatewayForumTopicCommentReactionCreatedEvent::class)
                subclass(GatewayForumTopicCommentReactionDeletedEvent::class)
                subclass(GatewayCalendarEventReactionCreatedEvent::class)
                subclass(GatewayCalendarEventReactionDeletedEvent::class)
                subclass(GatewayCalendarEventCommentReactionCreatedEvent::class)
                subclass(GatewayCalendarEventCommentReactionDeletedEvent::class)
                subclass(GatewayDocumentationReactionCreatedEvent::class)
                subclass(GatewayDocumentationReactionDeletedEvent::class)
                subclass(GatewayDocumentationCommentReactionCreatedEvent::class)
                subclass(GatewayDocumentationCommentReactionDeletedEvent::class)
                subclass(GatewayServerMemberSocialLinkCreatedEvent::class)
                subclass(GatewayServerMemberSocialLinkUpdatedEvent::class)
                subclass(GatewayServerMemberSocialLinkDeletedEvent::class)
            }
        }
    }
}

public interface EventDecoder {
    public fun decodeEventFromPayload(payload: String): GatewayEvent?
}

public class DefaultEventDecoder(private val gatewayId: Int, public val logger: DeckLogger?) : EventDecoder {
    @Suppress("UNCHECKED_CAST")
    @OptIn(ExperimentalSerializationApi::class)
    override fun decodeEventFromPayload(payload: String): GatewayEvent? = runCatching {
        val barebones = Json.decodeFromString<BarebonesEvent>(payload)
        val eventData = barebones.data.asNullable() ?: JsonObject(emptyMap())
        val eventType = barebones.type.asNullable() ?: when (barebones.opcode) {
            GatewayOpcode.Dispatch -> error("Invalid event type")
            GatewayOpcode.Hello -> "HelloEvent"
            GatewayOpcode.Resume -> "ResumeEvent"
            else -> error("Unknown opcode ${barebones.opcode}")
        }
        val deserializationStrategy = polymorphicJson.serializersModule.getPolymorphic(GatewayEvent::class, eventType)
            ?: return run {
//                TODO: turn this back on when duplicate events are fixed
//                logger?.error { "Unknown event type `$eventType`" }
                null
            }
        val event = polymorphicJson.decodeFromJsonElement(deserializationStrategy as DeserializationStrategy<GatewayEvent>, eventData)
        event._info = EventInfo(
            opcode = barebones.opcode,
            data = eventData,
            lastMessageId = barebones.messageId.asNullable(),
            type = eventType,
            gatewayId = gatewayId
        )
        return@runCatching event
    }.onFailure { it.printStackTrace() }.getOrNull()
}

public data class EventInfo(
    val opcode: Int,
    val data: JsonObject,
    val lastMessageId: String?,
    val type: String,
    val gatewayId: Int
)

@Serializable
private data class BarebonesEvent(
    @SerialName("op")
    val opcode: Int,
    @SerialName("d")
    val data: OptionalProperty<JsonObject> = OptionalProperty.NotPresent,
    @SerialName("s")
    val messageId: OptionalProperty<String> = OptionalProperty.NotPresent,
    @SerialName("t")
    val type: OptionalProperty<String> = OptionalProperty.NotPresent
)