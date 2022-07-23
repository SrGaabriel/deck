package io.github.deck.core.stateless.channel

import io.github.deck.common.util.IntGenericId
import io.github.deck.core.entity.ForumTopic
import io.github.deck.core.entity.impl.DeckForumTopic
import io.github.deck.rest.builder.CreateForumTopicRequestBuilder
import io.github.deck.rest.builder.UpdateForumTopicRequestBuilder

public interface StatelessForumChannel: StatelessServerChannel {
    /**
     * Creates a new forum topic within this channel
     *
     * @param builder topic builder
     * @return created forum topic
     */
    public suspend fun createTopic(builder: CreateForumTopicRequestBuilder.() -> Unit): ForumTopic =
        DeckForumTopic.from(client, client.rest.channel.createForumTopic(id, builder))

    /**
     * Patches the forum topic with the provided id ([forumTopicId]) in this channel
     *
     * @param forumTopicId forum topic id
     * @param builder update builder
     *
     * @return updated forum topic
     */
    public suspend fun patchTopic(forumTopicId: IntGenericId, builder: UpdateForumTopicRequestBuilder.() -> Unit): ForumTopic =
        DeckForumTopic.from(client, client.rest.channel.updateForumTopic(id, forumTopicId, builder))

    /**
     * Deletes the forum topic with the provided id ([forumTopicId]) from this channel
     *
     * @param forumTopicId forum topic id
     */
    public suspend fun deleteTopic(forumTopicId: IntGenericId): Unit =
        client.rest.channel.deleteForumTopic(id, forumTopicId)
}