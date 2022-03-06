package com.deck.core.entity.impl.channel

import com.deck.common.content.Content
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.DeckClient
import com.deck.core.entity.channel.ForumChannel
import com.deck.core.entity.channel.ForumPost
import com.deck.core.entity.channel.ForumThread
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
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
    override val archivedAt: Instant?,
    override val creatorId: GenericId,
    override val archiverId: GenericId?,
    override val updatedAt: Instant?,
    override val deletedAt: Instant?,
    override val teamId: GenericId
): ForumChannel

public data class DeckForumThread(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val title: String,
    override val originalPost: ForumPost,
    override val authorId: GenericId,
    override val channelId: UUID,
    override val teamId: GenericId,
    override val createdAt: Instant,
    override val editedAt: Instant?,
    override val isSticky: Boolean,
    override val isShare: Boolean,
    override val isLocked: Boolean,
    override val isDeleted: Boolean,
): ForumThread

public data class DeckForumPost(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val content: Content,
    override val threadId: IntGenericId,
    override val channelId: UUID,
    override val authorId: GenericId,
    override val teamId: GenericId,
    override val createdAt: Instant
): ForumPost