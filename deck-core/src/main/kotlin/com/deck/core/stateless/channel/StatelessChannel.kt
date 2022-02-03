package com.deck.core.stateless.channel

import com.deck.common.content.node.Node
import com.deck.common.util.Mentionable
import com.deck.common.util.mapToModel
import java.util.*

public interface StatelessChannel: Mentionable {
    public val id: UUID

    override fun getMentionNode(): Node = Node.Mention.Channel(id.mapToModel())
}
