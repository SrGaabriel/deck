package com.deck.core.entity.impl

import com.deck.common.content.Content
import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.core.DeckClient
import com.deck.core.entity.Channel
import com.deck.core.entity.Message
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import java.util.*

public data class DeckMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val content: Content,
    override val teamId: GenericId?,
    override val channelId: UUID,
    override val repliesToId: UUID?,
    override val createdAt: Timestamp,
    override val updatedAt: Timestamp?,
    override val createdBy: GenericId,
    override val updatedBy: GenericId?,
    override val isSilent: Boolean,
    override val isPrivate: Boolean
) : Message {
    public val channel: Deferred<Channel> = client.entityDelegator.async(start = CoroutineStart.LAZY) { getChannel() }

    override suspend fun getChannel(): Channel =
        client.entityDelegator.getChannel(channelId, teamId)!!
}
