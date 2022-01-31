package com.deck.core.entity.impl.channel

import com.deck.common.content.Content
import com.deck.common.util.IntGenericId
import com.deck.core.DeckClient
import com.deck.core.entity.channel.ForumChannel
import com.deck.core.entity.channel.ForumThread
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessForumChannel
import kotlinx.datetime.Instant
import java.util.*

public data class DeckForumChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val description: String,
    override val type: ChannelType,
    override val contentType: ChannelContentType,
    override val createdAt: Instant,
    override val createdBy: StatelessUser,
    override val archivedAt: Instant?,
    override val archivedBy: StatelessUser?,
    override val updatedAt: Instant?,
    override val deletedAt: Instant?,
    override val team: StatelessTeam
): ForumChannel

public data class DeckForumThread(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val title: String,
    override val content: Content,
    override val channel: BlankStatelessForumChannel,
    override val createdAt: Instant,
    override val createdBy: StatelessUser,
    override val editedAt: Instant?,
    override val isSticky: Boolean,
    override val isShare: Boolean,
    override val isLocked: Boolean,
    override val isDeleted: Boolean,
    override val team: StatelessTeam
): ForumThread