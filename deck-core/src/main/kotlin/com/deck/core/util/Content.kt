@file:Suppress("unused")

package com.deck.core.util

import com.deck.common.content.ParagraphBuilder
import com.deck.common.content.node.Node
import com.deck.common.util.mapToModel
import com.deck.core.stateless.StatelessRole
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.standard.StandardStatelessChannel
import java.util.*

public fun ParagraphBuilder.channel(id: UUID): Node.Mention.Channel = this.channel(id.mapToModel())

public fun ParagraphBuilder.channel(channel: StandardStatelessChannel): Node = channel.getMentionNode()

public fun ParagraphBuilder.user(user: StatelessUser): Node = user.getMentionNode()

public fun ParagraphBuilder.role(role: StatelessRole): Node = role.getMentionNode()