package io.github.deck.core.stateless.channel

import io.github.deck.core.entity.ForumThread
import io.github.deck.core.entity.impl.DeckForumThread
import io.github.deck.rest.builder.CreateForumThreadRequestBuilder

public interface StatelessForumChannel: StatelessServerChannel {
    /**
     * Creates a new forum thread within this channel
     *
     * @param builder thread builder
     * @return created forum thread
     */
    public suspend fun createThread(builder: CreateForumThreadRequestBuilder.() -> Unit): ForumThread =
        DeckForumThread.from(client, client.rest.channel.createForumThread(id, builder))
}