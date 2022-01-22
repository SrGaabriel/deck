package com.deck.core.cache

import com.deck.common.util.GenericId
import com.deck.core.entity.Channel
import com.deck.core.entity.User
import com.github.benmanes.caffeine.cache.Cache
import java.util.*

public interface CacheManager {
    public val channels: Cache<UUID, Channel>
    public val users: Cache<GenericId, User>

    public fun updateChannel(newChannel: Channel)

    public fun updateUser(newUser: User)

    public fun retrieveChannel(channelId: UUID): Channel?

    public fun retrieveUser(userId: GenericId): User?

    public fun putChannel(channel: Channel)

    public fun putUser(user: User)

    public fun removeChannel(channelId: UUID)

    public fun removeUser(userId: GenericId)
}