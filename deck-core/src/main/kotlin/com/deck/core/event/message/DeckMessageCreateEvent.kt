package com.deck.core.event.message

import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Channel
import com.deck.core.entity.Message
import com.deck.core.entity.User
import com.deck.core.entity.impl.DeckMessage
import com.deck.core.entity.misc.mapToContent
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.gateway.event.type.GatewayChatMessageCreatedEvent
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

public data class DeckMessageCreateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val message: Message,
    val channel: Channel,
    val teamId: GenericId?,
    val senderId: GenericId
) : DeckEvent {
    public suspend fun getSender(): User? =
        client.entityDelegator.getUser(senderId)

    public companion object : EventMapper<GatewayChatMessageCreatedEvent, DeckMessageCreateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageCreatedEvent): DeckMessageCreateEvent =
            DeckMessageCreateEvent(
                client = client,
                gatewayId = event.gatewayId,
                message = DeckMessage(
                    client = client,
                    id = event.message.id.mapToBuiltin(),
                    content = event.message.content.mapToContent(),
                    teamId = event.teamId.asNullable(),
                    repliesToId = event.message.repliesTo?.mapToBuiltin(),
                    createdAt = event.createdAt,
                    createdBy = event.createdBy,
                    updatedAt = null,
                    updatedBy = null,
                    channelId = event.channelId.mapToBuiltin(),
                    isSilent = event.message.isSilent.asNullable() == true,
                    isPrivate = event.message.isPrivate.asNullable() == true
                ),
                senderId = event.message.createdBy,
                teamId = event.teamId.asNullable(),
                channel = client.entityDelegator.getChannel(event.channelId.mapToBuiltin(), event.teamId.asNullable())!!
            )

        override val coroutineContext: CoroutineContext = Dispatchers.Unconfined
    }
}
