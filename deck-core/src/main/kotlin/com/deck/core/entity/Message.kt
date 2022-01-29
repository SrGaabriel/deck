package com.deck.core.entity

import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant
import java.util.*

public interface Message : Entity, StatelessMessage {
    public val content: String

    public val author: StatelessUser
    public val server: StatelessServer?

    public val createdAt: Instant
    public val updatedAt: Instant?

    public val repliesTo: List<UUID>
    public val isPrivate: Boolean
}