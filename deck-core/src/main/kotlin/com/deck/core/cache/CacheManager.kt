package com.deck.core.cache

import com.deck.common.util.GenericId
import com.deck.core.entity.Channel
import com.deck.core.entity.Team
import com.deck.core.entity.User
import com.github.benmanes.caffeine.cache.Cache
import java.util.*

public interface CacheManager {
    public val channels: Cache<UUID, Channel>
    public val users: Cache<GenericId, User>
    public val teams: Cache<GenericId, Team>

    public fun updateChannel(id: UUID, channel: Channel?)

    public fun retrieveChannel(id: UUID): Channel?

    public fun updateUser(id: GenericId, user: User?)

    public fun retrieveUser(id: GenericId): User?

    public fun retrieveTeam(id: GenericId): Team?

    public fun updateTeam(id: GenericId, team: Team?)
}