package com.deck.core.stateless.generic

import com.deck.common.content.node.Node
import com.deck.common.util.Mentionable
import com.deck.common.util.mapToModel
import com.deck.core.DeckClient
import com.deck.core.entity.channel.Channel
import com.deck.core.stateless.StatelessTeam
import java.util.*

public interface GenericStatelessChannel: Mentionable {
    public val client: DeckClient
    public val id: UUID
    public val team: StatelessTeam?

    override fun getMentionNode(): Node = Node.Mention.Channel(id.mapToModel())

    public suspend fun getState(): Channel =
        client.entityDelegator.getChannel(id, team?.id)
            ?: error("Tried to access an invalid channel's state")
}