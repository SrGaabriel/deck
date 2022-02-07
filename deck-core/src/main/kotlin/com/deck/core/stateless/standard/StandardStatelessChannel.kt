package com.deck.core.stateless.standard

import com.deck.common.content.node.Node
import com.deck.common.util.Mentionable
import com.deck.common.util.mapToModel
import java.util.*

public interface StandardStatelessChannel: Mentionable {
    public val id: UUID

    override fun getMentionNode(): Node = Node.Mention.Channel(id.mapToModel())
}