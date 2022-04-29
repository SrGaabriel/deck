package io.github.deck.core.event.message

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.asNullable
import io.github.deck.common.util.mapToBuiltin
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Message
import io.github.deck.core.entity.impl.DeckMessage
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.stateless.channel.StatelessMessageChannel
import io.github.deck.core.util.BlankStatelessMessageChannel
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.core.util.BlankStatelessUser
import io.github.deck.gateway.event.type.GatewayChatMessageCreatedEvent
import java.util.*

public data class MessageCreateEvent(
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

    public companion object: EventMapper<GatewayChatMessageCreatedEvent, MessageCreateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageCreatedEvent): MessageCreateEvent =
            MessageCreateEvent(
                client = client,
                gatewayId = event.gatewayId,
                message = DeckMessage.from(client, event.message),
                channelId = event.message.channelId.mapToBuiltin(),
                authorId = event.message.createdBy,
                serverId = event.message.serverId.asNullable()
            )
    }
}