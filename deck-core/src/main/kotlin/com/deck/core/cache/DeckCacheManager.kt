package com.deck.core.cache

import com.deck.common.util.GenericId
import com.deck.core.entity.Channel
import com.deck.core.entity.User
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import java.util.*

public class DeckCacheManager() : CacheManager {
    override val channels: Cache<UUID, Channel> = Caffeine.newBuilder().build<UUID, Channel>()
    override val users: Cache<GenericId, User> = Caffeine.newBuilder().build<GenericId, User>()

    override fun updateChannel(newChannel: Channel): Unit =
        channels.put(newChannel.id, newChannel)

    override fun updateUser(newUser: User): Unit =
        users.put(newUser.id, newUser)

    override fun retrieveChannel(channelId: UUID): Channel? =
        channels.getIfPresent(channelId)

    override fun retrieveUser(userId: GenericId): User? =
        users.getIfPresent(userId)

    override fun putChannel(channel: Channel): Unit =
        channels.put(channel.id, channel)

    override fun putUser(user: User): Unit =
        users.put(user.id, user)

    override fun removeChannel(channelId: UUID): Unit =
        channels.invalidate(channelId)

    override fun removeUser(userId: GenericId): Unit =
        users.invalidate(userId)
}