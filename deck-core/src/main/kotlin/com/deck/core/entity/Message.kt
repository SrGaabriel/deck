package com.deck.core.entity

import com.deck.common.content.Content
import com.deck.core.entity.channel.Channel
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant
import java.util.*

public interface Message : Entity, StatelessMessage {
    public val content: Content

    public val team: StatelessTeam?

    public val repliesToId: UUID?

    public val createdAt: Instant
    public val updatedAt: Instant?

    public val author: StatelessUser
    public val editor: StatelessUser?

    public val isPrivate: Boolean

    public suspend fun getChannel(): Channel = channel.getState()
}