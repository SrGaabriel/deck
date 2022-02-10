package com.deck.core.cache

import com.deck.common.util.GenericId
import com.deck.core.entity.Member
import com.deck.core.entity.Message
import com.deck.core.entity.Team
import com.deck.core.entity.User
import com.deck.core.entity.channel.Channel
import com.deck.core.util.putOrInvalidate
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import java.util.*

public class DeckCacheManager : CacheManager {
    override val channels: Cache<UUID, Channel> = Caffeine.newBuilder().build()
    override val users: Cache<GenericId, User> = Caffeine.newBuilder().build()
    override val teams: Cache<GenericId, Team> = Caffeine.newBuilder().build()
    override val messages: Cache<UUID, Message> = Caffeine.newBuilder().build()
    override val members: Cache<GenericId, Map<GenericId, Member>> = Caffeine.newBuilder().build()

    override fun updateChannel(id: UUID, channel: Channel?) {
        channels.putOrInvalidate(id, channel)
    }

    override fun retrieveChannel(id: UUID): Channel? {
        return channels.getIfPresent(id)
    }

    override fun updateUser(id: GenericId, user: User?) {
        users.putOrInvalidate(id, user)
    }

    override fun retrieveUser(id: GenericId): User? {
        return users.getIfPresent(id)
    }

    override fun updateTeam(id: GenericId, team: Team?) {
        teams.putOrInvalidate(id, team)
    }

    override fun retrieveTeam(id: GenericId): Team? {
        return teams.getIfPresent(id)
    }

    override fun updateMessage(id: UUID, message: Message?) {
        messages.putOrInvalidate(id, message)
    }

    override fun retrieveMessage(id: UUID): Message? {
        return messages.getIfPresent(id)
    }

    override fun updateMember(id: GenericId, teamId: GenericId, member: Member?) {
        val members = retrieveMembers(teamId).orEmpty().associateBy { it.id }.toMutableMap()
        if (member == null) members.remove(id) else members[id] = member
        updateMembers(teamId, members)
    }

    override fun retrieveMember(id: GenericId, teamId: GenericId): Member? {
        return retrieveMembers(teamId)?.firstOrNull { it.id == id }
    }

    override fun updateMembers(teamId: GenericId, members: Map<GenericId, Member>) {
        this.members.putOrInvalidate(teamId, members)
    }

    override fun retrieveMembers(teamId: GenericId): List<Member>? {
        return members.getIfPresent(teamId)?.values?.toList()
    }
}