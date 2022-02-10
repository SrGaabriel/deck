package com.deck.core.cache

import com.deck.common.util.GenericId
import com.deck.core.entity.Member
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
    public val messages: Cache<UUID, Message>
    public val members: Cache<GenericId, Map<GenericId, Member>>

    public fun updateChannel(id: UUID, channel: Channel?)

    public fun retrieveChannel(id: UUID): Channel?

    public fun updateUser(id: GenericId, user: User?)

    public fun retrieveUser(id: GenericId): User?

    public fun updateTeam(id: GenericId, team: Team?)

    public fun retrieveTeam(id: GenericId): Team?

    public fun updateMessage(id: UUID, message: Message?)

    public fun retrieveMessage(id: UUID): Message?

    public fun updateMember(id: GenericId, teamId: GenericId, member: Member?)

    public fun retrieveMember(id: GenericId, teamId: GenericId): Member?

    public fun updateMembers(teamId: GenericId, members: Map<GenericId, Member>)

    public fun retrieveMembers(teamId: GenericId): List<Member>?
}