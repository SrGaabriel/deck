package io.github.deck.core.stateless.channel

import io.github.deck.core.entity.ForumTopic
import io.github.deck.core.entity.impl.DeckForumTopic
import io.github.deck.rest.builder.CreateForumTopicRequestBuilder

public interface StatelessForumChannel: StatelessServerChannel {
    /**
     * Creates a new forum topic within this channel
     *
     * @param builder topic builder
     * @return created forum topic
     */
    public suspend fun createTopic(builder: CreateForumTopicRequestBuilder.() -> Unit): ForumTopic =
        DeckForumTopic.from(client, client.rest.channel.createForumTopic(id, builder))
}