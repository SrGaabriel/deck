package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.entity.Channel
import com.deck.core.entity.Message
import com.deck.core.util.sendMessage
import java.util.*

public interface StatelessMessageChannel: StatelessEntity<Channel> {
    public val id: UUID
    public val teamId: GenericId?

    public suspend fun sendMessage(builder: DeckMessageBuilder.() -> Unit): Message =
        client.entityStrategizer.decodePartialSentMessage(
            id,
            teamId,
            client.rest.channelRoute.sendMessage(id, builder).message
        )

    // TODO: Try to access cache first instead of directly resorting to rest
    override suspend fun getState(): Channel {
        return client.entityDelegator.getChannel(id, teamId)
            ?: error("Tried to access a deleted channel's state")
    }
}
