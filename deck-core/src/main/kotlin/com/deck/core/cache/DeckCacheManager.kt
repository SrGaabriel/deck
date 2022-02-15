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
import java.util.concurrent.TimeUnit

public class DeckCacheManager : CacheManager {
    private val channels: Cache<UUID, Channel> = buildStandardCache()
    private val users: Cache<GenericId, User> = buildStandardCache()
    private val teams: Cache<GenericId, Team> = buildStandardCache()
    private val messages: Cache<UUID, Message> = buildStandardCache()
    private val members: Cache<GenericId, Map<GenericId, Member>> = buildStandardCache()

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
        val members = retrieveMembers(teamId).orEmpty().toMutableMap()
        if (member == null)
            members.remove(id) else members[id] = member
        updateMembers(teamId, members)
    }

    override fun retrieveMember(id: GenericId, teamId: GenericId): Member? {
        return retrieveMembers(teamId)?.get(id)
    }

    override fun updateMembers(teamId: GenericId, members: Map<GenericId, Member>) {
        this.members.putOrInvalidate(teamId, members)
    }

    override fun retrieveMembers(teamId: GenericId): Map<GenericId, Member>? {
        return members.getIfPresent(teamId)
    }

    override fun retrieveAllMembersOfId(id: GenericId): List<Member> {
        return members.asMap()
            .flatMap { it.value.values }
            .filter { it.id == id }
    }

    private fun <K, V> buildStandardCache(): Cache<K, V> = Caffeine
        .newBuilder()
        .expireAfterWrite(1, TimeUnit.HOURS)
        .build()
}