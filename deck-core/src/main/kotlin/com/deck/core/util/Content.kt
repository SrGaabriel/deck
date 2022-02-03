package com.deck.core.util

import com.deck.common.content.ParagraphBuilder
import com.deck.common.content.node.Node
import com.deck.common.util.mapToModel
import com.deck.core.entity.Entity
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessRole
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessChannel
import kotlinx.serialization.json.JsonPrimitive
import java.util.*

public fun ParagraphBuilder.channel(id: UUID): Node.Mention.Channel = this.channel(id.mapToModel())

@Suppress("unused")
public fun <T : Entity> ParagraphBuilder.entity(entity: StatelessEntity<T>): Node = when(entity) {
    is StatelessUser -> Node.Mention(JsonPrimitive(entity.id), "person")
    is StatelessRole -> Node.Mention(JsonPrimitive(entity.id), "role")
    is StatelessChannel -> Node.Mention.Channel(entity.id.mapToModel())
    else -> error("Provided entity type does not support mentions.")
}