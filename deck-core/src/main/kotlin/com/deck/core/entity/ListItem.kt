package com.deck.core.entity

import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessListChannel
import kotlinx.datetime.Instant
import java.util.*

public interface ListItem: Entity {
    public val id: UUID

    public val server: StatelessServer
    public val channel: StatelessListChannel

    public val label: String
    public val note: String?

    public val createdBy: StatelessUser

    public val createdAt: Instant
}