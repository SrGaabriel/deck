package com.deck.core.event.message

import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.entity.User
import com.deck.core.entity.impl.DeckMessage
import com.deck.core.entity.misc.mapToContent
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.gateway.event.type.GatewayChatMessageCreatedEvent

data class DeckMessageCreateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val message: Message,
    val teamId: GenericId?,
    val senderId: GenericId
): DeckEvent {
    private val _sender: User? = null

    suspend fun getSender(): User =
        _sender ?: client.userService.getUser(senderId)!!

    companion object: EventMapper<GatewayChatMessageCreatedEvent, DeckMessageCreateEvent> {
        override fun map(client: DeckClient, event: GatewayChatMessageCreatedEvent): DeckMessageCreateEvent =
            DeckMessageCreateEvent(
                client = client,
                gatewayId = event.gatewayId,
                message = DeckMessage(
                    id = event.message.id.mapToBuiltin(),
                    content = event.message.content.mapToContent(),
                    repliesToId = event.message.repliesTo?.mapToBuiltin(),
                    createdAt = event.createdAt,
                    createdBy = event.createdBy,
                    updatedAt = null,
                    updatedBy = null,
                    isSilent = event.message.isSilent.asNullable() == true,
                    isPrivate = event.message.isPrivate.asNullable() == true
                ),
                senderId = event.message.createdBy,
                teamId = event.teamId.asNullable()
            )
    }
}
