package com.deck.core.util

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Member
import com.deck.core.entity.SelfUser
import com.deck.core.entity.Team
import com.deck.core.entity.User
import com.deck.core.entity.channel.Channel
import java.util.*

public suspend fun DeckClient.getUser(id: GenericId): User? =
    entityDelegator.getUser(id)

public suspend fun DeckClient.getSelf(): SelfUser =
    entityDelegator.getSelfUser()

public suspend fun DeckClient.getTeam(id: GenericId): Team? =
    entityDelegator.getTeam(id)

public suspend fun DeckClient.getMember(id: GenericId, teamId: GenericId): Member? =
    entityDelegator.getMember(id, teamId)

public suspend fun DeckClient.getChannel(id: UUID, teamId: GenericId? = null): Channel? =
    entityDelegator.getChannel(id, teamId)

public suspend fun DeckClient.getTeamChannel(id: UUID, teamId: GenericId): Channel? =
    entityDelegator.getTeamChannel(id, teamId)

public suspend fun DeckClient.getTeamMembers(teamId: GenericId): List<Member>? =
    entityDelegator.getTeamMembers(teamId)

public suspend fun DeckClient.getPrivateChannel(id: UUID): Channel? =
    entityDelegator.getPrivateChannel(id)