package com.deck.core.event.message

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.entity.impl.DeckMessage
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.core.util.BlankStatelessMessageChannel
import com.deck.core.util.BlankStatelessServer
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayChatMessageUpdatedEvent
import java.util.*

public data class DeckMessageUpdateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val message: Message,
    public val channelId: UUID,
    public val authorId: GenericId,
    public val serverId: GenericId?,
) : DeckEvent {
    val channel: StatelessMessageChannel get() = BlankStatelessMessageChannel(client, channelId, serverId)
    val author: StatelessUser get() = BlankStatelessUser(client, authorId)
    val server: StatelessServer? get() = serverId?.let { serverId -> BlankStatelessServer(client, serverId) }

    public companion object: EventMapper<GatewayChatMessageUpdatedEvent, DeckMessageUpdateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageUpdatedEvent): DeckMessageUpdateEvent {
            val message = DeckMessage.from(client, event.message)
            return DeckMessageUpdateEvent(
                client = client,
                gatewayId = event.gatewayId,
                message = message,
                channelId = message.channelId,
                serverId = message.serverId,
                authorId = message.authorId
            )
        }
    }
}