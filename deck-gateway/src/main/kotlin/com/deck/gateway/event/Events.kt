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
abstract class GatewayEvent {
    @Transient
    var gatewayId = -1
}

@OptIn(DeckExperimental::class)
private val serializationModule by lazy {
    SerializersModule {
        polymorphic(GatewayEvent::class) {
            subclass(GatewayTeamXpAddedEvent::class)
            subclass(GatewayChannelTypingEvent::class)
            subclass(GatewayChatMessageCreatedEvent::class)
            subclass(GatewayChatMessageDeleteEvent::class)
            subclass(GatewayTeamChannelCreatedEvent::class)
            subclass(GatewayTeamChannelUpdatedEvent::class)
            subclass(GatewayTeamChannelDeletedEvent::class)
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
            subclass(GatewayTeamChannelCategoryGroupMovedEvent::class)
            subclass(GatewayTeamGroupCreatedEvent::class)
            subclass(GatewayTeamGroupUpdatedEvent::class)
            subclass(GatewayChannelBadgedEvent::class)
            subclass(GatewayChatMessageReactionAddedEvent::class)
            subclass(GatewayChatMessageReactionDeletedEvent::class)
            subclass(GatewayUserStreamsVisibilityUpdatedEvent::class)
            subclass(GatewayTeamChannelStreamRemovedEvent::class)
            subclass(GatewayTeamChannelStreamEndedEvent::class)
            subclass(GatewayTeamChannelVoiceParticipantAddedEvent::class)
            subclass(GatewayTeamChannelVoiceParticipantRemovedEvent::class)
            subclass(GatewayChatChannelBroadcastCallEvent::class)
            subclass(GatewayChatChannelBroadcastCallResponseEvent::class)
            subclass(GatewayPrivateChatChannelCreatedEvent::class)
            subclass(GatewayChatChannelUpdatedEvent::class)
        }
    }
}

private val forgivingJson by lazy {
    Json {
        ignoreUnknownKeys = true
        serializersModule = serializationModule
    }
}

interface EventDecoder {
    fun decodeEventFromPayload(payload: Payload): GatewayEvent?

    fun decodePayloadFromString(string: String): Payload?
}

class DefaultEventDecoder(private val gatewayId: Int): EventDecoder {
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
        if (string.startsWith("0{")) // Handle payload event (different opcode)
            return Payload("Hello", string.substring(1))
        val payload = Payload(
            type = string.substringAfter('"').substringBefore('"'),
            json = string.substringAfter(',').substringBeforeLast(']')
        )
        return if (payload.isValid) payload else null
    }
}

data class Payload(val type: String, val json: String) {
    val isValid = type != json && json.contains("{")
}