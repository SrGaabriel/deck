package com.deck.core.util

import com.deck.common.util.DeckObsoleteApi
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.DeckClient
import com.deck.core.stateless.*
import com.deck.core.stateless.channel.*
import com.deck.core.stateless.generic.GenericStatelessChannel
import java.util.*

internal data class BlankStatelessChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val teamId: GenericId?
): GenericStatelessChannel

/**
 * This method is obsolete and should be replaced with
 * specific alternatives, like [StatelessForumChannel], [StatelessMessageChannel], etc...
 *
 * It should only be used to reference obtainable channels, of which you can mention
 * or fetch states.
 */
@DeckObsoleteApi
@Suppress("FunctionName")
public fun StatelessChannel(
    client: DeckClient,
    id: UUID,
    teamId: GenericId?
): GenericStatelessChannel = BlankStatelessChannel(client, id, teamId)

internal data class BlankStatelessMember(
    override val client: DeckClient,
    override val id: GenericId,
    override val teamId: GenericId
): StatelessMember

public fun StatelessMember(
    client: DeckClient,
    id: GenericId,
    teamId: GenericId
): StatelessMember = BlankStatelessMember(client, id, teamId)

internal data class BlankStatelessMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val channelId: UUID,
    override val teamId: GenericId?
): StatelessMessage

public fun StatelessMessage(
    client: DeckClient,
    id: UUID,
    channelId: UUID,
    teamId: GenericId?
): StatelessMessage = BlankStatelessMessage(client, id, channelId, teamId)

internal data class BlankStatelessMessageChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val teamId: GenericId?
): StatelessMessageChannel

public fun StatelessMessageChannel(
    client: DeckClient,
    id: UUID,
    teamId: GenericId?
): StatelessMessageChannel = BlankStatelessMessageChannel(client, id, teamId)

internal data class BlankStatelessForumChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val teamId: GenericId
): StatelessForumChannel

public fun StatelessForumChannel(
    client: DeckClient,
    id: UUID,
    teamId: GenericId
): StatelessForumChannel = BlankStatelessForumChannel(client, id, teamId)

internal data class BlankStatelessForumThread(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val teamId: GenericId,
    override val channelId: UUID
): StatelessForumThread

public fun StatelessForumThread(
    client: DeckClient,
    id: IntGenericId,
    teamId: GenericId,
    channelId: UUID
): StatelessForumThread = BlankStatelessForumThread(client, id, teamId, channelId)

internal data class BlankStatelessForumPost(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val threadId: IntGenericId,
    override val teamId: GenericId,
    override val channelId: UUID
): StatelessForumPost

internal data class BlankStatelessTeam(
    override val client: DeckClient,
    override val id: GenericId
): StatelessTeam

public fun StatelessTeam(
    client: DeckClient,
    id: GenericId
): StatelessTeam = BlankStatelessTeam(client, id)

internal data class BlankStatelessUser(
    override val client: DeckClient,
    override val id: GenericId
): StatelessUser

public fun StatelessUser(
    client: DeckClient,
    id: GenericId
): StatelessUser = BlankStatelessUser(client, id)

internal data class BlankStatelessRole(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val teamId: GenericId
): StatelessRole

public fun StatelessRole(
    client: DeckClient,
    id: IntGenericId,
    teamId: GenericId
): StatelessRole = BlankStatelessRole(client, id, teamId)

internal data class BlankStatelessSchedulingChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val teamId: GenericId
): StatelessSchedulingChannel

public fun StatelessSchedulingChannel(
    client: DeckClient,
    id: UUID,
    teamId: GenericId
): StatelessSchedulingChannel = BlankStatelessSchedulingChannel(client, id, teamId)

internal data class BlankStatelessScheduleAvailability(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val teamId: GenericId,
    override val channelId: UUID
): StatelessScheduleAvailability

public fun StatelessScheduleAvailability(
    client: DeckClient,
    id: IntGenericId,
    teamId: GenericId,
    channelId: UUID
): StatelessScheduleAvailability = BlankStatelessScheduleAvailability(client, id, teamId, channelId)