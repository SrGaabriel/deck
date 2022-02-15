package com.deck.core.cache

import com.deck.common.util.GenericId
import com.deck.core.entity.Member
import com.deck.core.entity.Message
import com.deck.core.entity.Team
import com.deck.core.entity.User
import com.deck.core.entity.channel.Channel
import java.util.*

public interface CacheManager {
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

    public fun retrieveMembers(teamId: GenericId): Map<GenericId, Member>?

    public fun retrieveAllMembersOfId(id: GenericId): List<Member>
}