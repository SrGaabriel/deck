package com.deck.core.cache

import com.deck.common.util.GenericId
import com.deck.common.util.UniqueId
import com.deck.core.entity.Message
import com.deck.core.entity.Team
import com.deck.core.entity.User
import com.deck.core.entity.channel.Channel
import com.github.benmanes.caffeine.cache.Cache
import java.util.*

public interface CacheManager {
    public val channels: Cache<UUID, Channel>
    public val users: Cache<GenericId, User>
    public val teams: Cache<GenericId, Team>
    public val messages: Cache<UniqueId, Message>

    public fun updateChannel(id: UUID, channel: Channel?)

    public fun retrieveChannel(id: UUID): Channel?

    public fun updateUser(id: GenericId, user: User?)

    public fun retrieveUser(id: GenericId): User?

    public fun retrieveTeam(id: GenericId): Team?

    public fun updateTeam(id: GenericId, team: Team?)

    public fun retrieveMessage(id: UniqueId): Message?

    public fun updateMessage(id: UniqueId, message: Message?)
}