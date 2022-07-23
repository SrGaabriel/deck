package io.github.deck.core.stateless

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.core.entity.ForumTopic
import io.github.deck.core.entity.impl.DeckForumTopic
import io.github.deck.core.stateless.channel.StatelessForumChannel
import io.github.deck.core.util.BlankStatelessForumChannel
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.rest.builder.UpdateForumTopicRequestBuilder
import java.util.*

public interface StatelessForumTopic: StatelessEntity {
    public val id: IntGenericId
    public val channelId: UUID
    public val serverId: GenericId

    public val channel: StatelessForumChannel get() = BlankStatelessForumChannel(client, channelId, serverId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * **Patches** this forum topic
     *
     * @param builder update builder
     *
     * @return updated forum topic
     */
    public suspend fun patch(builder: UpdateForumTopicRequestBuilder.() -> Unit): ForumTopic =
        DeckForumTopic.from(client, client.rest.channel.updateForumTopic(channelId, id, builder))

    /**
     * Deletes this forum topic
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteForumTopic(channelId, id)
}