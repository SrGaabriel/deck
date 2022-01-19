@file:OptIn(DeckExperimental::class)

package com.deck.gateway.event

import com.deck.common.util.DeckExperimental
import com.deck.gateway.event.type.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
public abstract class GatewayEvent {
    @Transient
    public open var gatewayId: Int = -1
}

@OptIn(DeckExperimental::class)
private val serializationModule by lazy {
    SerializersModule {
        polymorphic(GatewayEvent::class) {
            subclass(GatewayTeamXpAddedEvent::class)
            subclass(GatewayChannelTypingEvent::class)
            subclass(GatewayChatMessageCreatedEvent::class)
            subclass(GatewayChatMessageUpdatedEvent::class)
            subclass(GatewayChatMessageDeleteEvent::class)
            subclass(GatewayTeamChannelCreatedEvent::class)
            subclass(GatewayTeamChannelUpdatedEvent::class)
            subclass(GatewayTeamChannelDeletedEvent::class)
            subclass(GatewayTeamChannelsDeletedEvent::class)
            subclass(GatewayTeamGroupArchivedEvent::class)
            subclass(GatewayTeamGroupRestoredEvent::class)
            subclass(GatewayTeamMemberJoinedEvent::class)
            subclass(GatewayTeamMemberUpdatedEvent::class)
            subclass(GatewayTeamMemberRemovedEvent::class)
            subclass(GatewayTeamRolesUpdatedEvent::class)
            subclass(GatewayTeamApplicationCreatedEvent::class)
            subclass(GatewayTeamApplicationUpdatedEvent::class)
            subclass(GatewayTeamApplicationRemovedEvent::class)
            subclass(GatewayTeamChannelCategoryCreatedEvent::class)
            subclass(GatewayTeamChannelCategoryUpdatedEvent::class)
            subclass(GatewayTeamChannelCategoryDeletedEvent::class)
            subclass(GatewayTeamChannelCategoriesDeletedEvent::class)
            subclass(GatewayTeamChannelCategoryGroupMovedEvent::class)
            subclass(GatewayTeamGroupCreatedEvent::class)
            subclass(GatewayTeamGroupUpdatedEvent::class)
            subclass(GatewayTeamGroupDeletedEvent::class)
            subclass(GatewayChannelBadgedEvent::class)
            subclass(GatewayChatMessageReactionAddedEvent::class)
            subclass(GatewayChatMessageReactionDeletedEvent::class)
            subclass(GatewayUserStreamsVisibilityUpdatedEvent::class)
            subclass(GatewayTeamChannelStreamAddedEvent::class)
            subclass(GatewayTeamChannelStreamActiveEvent::class)
            subclass(GatewayTeamChannelStreamRemovedEvent::class)
            subclass(GatewayTeamChannelStreamEndedEvent::class)
            subclass(GatewayTeamChannelVoiceParticipantAddedEvent::class)
            subclass(GatewayTeamChannelVoiceParticipantRemovedEvent::class)
            subclass(GatewayChatChannelBroadcastCallEvent::class)
            subclass(GatewayChatChannelBroadcastCallResponseEvent::class)
            subclass(GatewayPrivateChatChannelCreatedEvent::class)
            subclass(GatewayChatChannelUpdatedEvent::class)
            subclass(GatewayTeamReactionsUpdatedEvent::class)
            subclass(GatewayTeamReactionRemovedEvent::class)
            subclass(GatewayTemporalChannelCreatedEvent::class)
            subclass(GatewayTemporalChannelUsersAddedEvent::class)
            subclass(GatewayChatPinnedMessageCreatedEvent::class)
            subclass(GatewayChatPinnedMessageDeletedEvent::class)
            subclass(GatewayTeamBotCreatedEvent::class)
            subclass(GatewayTeamBotUpdatedEvent::class)
            subclass(GatewayUserTeamsUpdated::class)
            subclass(GatewaySelfUserPingedEvent::class)
            subclass(GatewaySelfChannelSeenEvent::class)
            subclass(GatewayUserTeamSectionSeen::class)
            subclass(GatewayTeamPaymentInfoUpdatedEvent::class)
            subclass(GatewayTeamServerSubscriptionPlanCreatedEvent::class)
            subclass(GatewayTeamServerSubscriptionPlanUpdatedEvent::class)
            subclass(GatewayTeamServerSubscriptionPlanDeletedEvent::class)
            subclass(GatewayTeamUpdatedEvent::class)
            subclass(GatewayTeamMessagesDeletedEvent::class)
            subclass(GatewayTeamMutedMembersUpdated::class)
            subclass(GatewayTeamDeafenedMembersUpdatedEvent::class)
            subclass(GatewayTeamChannelPrioritiesUpdatedEvent::class)
            subclass(GatewayTeamChannelCategoryPrioritiesUpdatedEvent::class)
            subclass(GatewayTeamGameAddedEvent::class)
            subclass(GatewayTeamUserGroupPrioritiesUpdated::class)
            subclass(GatewayTeamChannelContentCreatedEvent::class)
            subclass(GatewayTeamChannelContentUpdatedEvent::class)
            subclass(GatewayTeamChannelContentDeletedEvent::class)
            subclass(GatewayTeamEventRemovedEvent::class)
            subclass(GatewayTeamChannelArchivedEvent::class)
            subclass(GatewayTeamChannelAvailabilitiesUpdatedEvent::class)
            subclass(GatewayTeamChannelAvailabilitiesRemovedEvent::class)
            subclass(GatewayTeamChannelContentReplyCreatedEvent::class)
            subclass(GatewayTeamChannelContentReplyUpdatedEvent::class)
            subclass(GatewayTeamChannelContentReplyDeletedEvent::class)
            subclass(GatewayTeamContentReactionsAddedEvent::class)
            subclass(GatewayTeamContentReactionsRemovedEvent::class)
            subclass(GatewayChatChannelNicknameChangedEvent::class)
            subclass(GatewayUserPresenceManuallySetEvent::class)
            subclass(GatewayUserUpdatedEvent::class)
            subclass(GatewayStageUpdatedEvent::class)
        }
    }
}

private val forgivingJson by lazy {
    Json {
        ignoreUnknownKeys = true
        serializersModule = serializationModule
    }
}

public interface EventDecoder {
    public fun decodeEventFromPayload(payload: Payload): GatewayEvent?

    public fun decodePayloadFromString(string: String): Payload?
}

public class DefaultEventDecoder(private val gatewayId: Int) : EventDecoder {
    /**
     * Events with a case in when are specifically the ones which don't have
     * come with a **type** parameter.
     */
    override fun decodeEventFromPayload(payload: Payload): GatewayEvent? = runCatching {
        when (payload.type) {
            "Hello" -> forgivingJson.decodeFromString<GatewayHelloEvent>(payload.json)
            "TeamXpSet" -> forgivingJson.decodeFromString<GatewayTeamXpSetEvent>(payload.json)
            else -> forgivingJson.decodeFromString(payload.json)
        }.also {
            it.gatewayId = gatewayId
        }
    }.onFailure { it.printStackTrace() }.getOrNull()

    override fun decodePayloadFromString(string: String): Payload? {
        if (string.startsWith("0{")) // Handle hello event (different structure)
            return Payload("Hello", string.substring(1))
        val payload = Payload(
            type = string.substringAfter('"').substringBefore('"'),
            json = string.substringAfter(',').substringBeforeLast(']')
        )
        return if (payload.isValid) payload else null
    }
}

public data class Payload(val type: String, val json: String) {
    public val isValid: Boolean = type != json && json.contains("{")
}
