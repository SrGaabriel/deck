package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessListChannel
import com.deck.core.util.BlankStatelessListChannel
import com.deck.core.util.BlankStatelessServer
import com.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant
import java.util.*

public interface ListItem: Entity {
    public val id: UUID

    public val authorId: GenericId
    public val serverId: GenericId
    public val channelId: UUID

    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    public val channel: StatelessListChannel get() = BlankStatelessListChannel(client, channelId, serverId)

    public val label: String
    public val note: String?

    public val createdAt: Instant
}