package com.deck.core.util

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Member
import com.deck.core.entity.SelfUser
import com.deck.core.entity.Team
import com.deck.core.entity.User
import com.deck.core.entity.channel.Channel
import com.deck.core.entity.channel.TeamChannel
import java.util.*

/**
 * Retrieves the user with the specified [id].
 *
 * @param id user id
 * @return user or null if not found.
 */
public suspend fun DeckClient.getUser(id: GenericId): User? =
    entityDelegator.getUser(id)

/**
 * Retrieves the self user.
 *
 * @return self user
 */
public suspend fun DeckClient.getSelf(): SelfUser =
    entityDelegator.getSelfUser()

/**
 * Retrieves the team with the specified [id].
 *
 * @param id team id
 * @return team or null if not found.
 */
public suspend fun DeckClient.getTeam(id: GenericId): Team? =
    entityDelegator.getTeam(id)

/**
 * Retrieves the member with the specified [id] in the specified guild [teamId].
 *
 * @param id member id
 * @param teamId guild id
 *
 * @return member or null if not found.
 */
public suspend fun DeckClient.getMember(id: GenericId, teamId: GenericId): Member? =
    entityDelegator.getMember(id, teamId)

/**
 * Retrieves the channel with the specified [id] in the specified [teamId].
 *
 * @param id channel id
 * @param teamId guild id, null if library is to search in private messages.
 *
 * @return channel or null if not found.
 */
public suspend fun DeckClient.getChannel(id: UUID, teamId: GenericId? = null): Channel? =
    entityDelegator.getChannel(id, teamId)

/**
 * Retrieves the team channel with the specified [id] in the specified guild [teamId].
 *
 * @param id channel id
 * @param teamId guild id
 *
 * @return channel or null if not found.
 */
public suspend fun DeckClient.getTeamChannel(id: UUID, teamId: GenericId): TeamChannel? =
    entityDelegator.getTeamChannel(id, teamId)

/**
 * Retrieves the private channel with the specified [id].
 *
 * @param id channel id
 *
 * @return channel or null if not found.
 */
public suspend fun DeckClient.getPrivateChannel(id: UUID): Channel? =
    entityDelegator.getPrivateChannel(id)

/**
 * Retrieves the members in the specified guild [teamId].
 *
 * @param teamId guild id
 *
 * @return members or null if guild was not found or members couldn't be obtained.
 */
public suspend fun DeckClient.getTeamMembers(teamId: GenericId): Collection<Member>? =
    entityDelegator.getTeamMembers(teamId)