package com.deck.core.stateless.channel

import com.deck.common.util.IntGenericId
import com.deck.core.entity.channel.ForumThread
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessTeam
import com.deck.rest.builder.CreateForumThreadBuilder

public interface StatelessForumThread: StatelessEntity<ForumThread> {
    public val id: IntGenericId
    public val team: StatelessTeam
    public val channel: StatelessForumChannel

    /**
     * Updates your forum thread.
     *
     * @param builder update builder
     */
    public suspend fun update(builder: CreateForumThreadBuilder.() -> Unit): Unit =
        client.rest.channelRoute.updateForumThread(channel.id, id, builder)

    /**
     * Locks and unlocks the current forum thread making
     * users unable to send any further messages on it.
     *
     * **Note:** If this thread is already locked, this method will unlock it.
     */
    public suspend fun lock(): Unit =
        client.rest.channelRoute.lockForumThread(channel.id, id)

    @Deprecated("Locks the thread if it's already unlocked", replaceWith = ReplaceWith("lock()"))
    public suspend fun unlock(): Unit = lock()

    /**
     * Pins and unpins the current forum thread making it
     * stand out from the rest. Guilded's API usually calls it `stick`.
     *
     * **Note:** If this thread is already locked, this method will unlock it.
     */
    public suspend fun pin(): Unit =
        client.rest.channelRoute.pinForumThread(channel.id, id)

    @Deprecated("Pins the thread if it's not pinned", replaceWith = ReplaceWith("lock()"))
    public suspend fun unpin(): Unit = pin()

    /**
     * Deletes your forum thread.
     */
    public suspend fun delete(): Unit =
        client.rest.channelRoute.deleteForumThread(channel.id, id)

    override suspend fun getState(): ForumThread =
        client.entityDelegator.getForumChannelThread(id, channel.id)
            ?: error("Tried to access an invalid forum thread state.")
}