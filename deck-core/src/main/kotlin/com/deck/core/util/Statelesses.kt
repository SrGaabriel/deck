package com.deck.core.util

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.DeckClient
import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.*
import java.util.*

internal data class BlankStatelessMember(
    override val client: DeckClient,
    override val id: GenericId,
    override val team: StatelessTeam
): StatelessMember

public fun StatelessMember(
    client: DeckClient,
    id: GenericId,
    team: StatelessTeam
): StatelessMember = BlankStatelessMember(client, id, team)

internal data class BlankStatelessMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val channel: StatelessMessageChannel
): StatelessMessage

public fun StatelessMessage(
    client: DeckClient,
    id: UUID,
    channel: StatelessMessageChannel
): StatelessMessage = BlankStatelessMessage(client, id, channel)

internal data class BlankStatelessMessageChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val team: StatelessTeam?
): StatelessMessageChannel

public fun StatelessMessageChannel(
    client: DeckClient,
    id: UUID,
    team: StatelessTeam?
): StatelessMessageChannel = BlankStatelessMessageChannel(client, id, team)

internal data class BlankStatelessForumChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val team: StatelessTeam
): StatelessForumChannel

public fun StatelessForumChannel(
    client: DeckClient,
    id: UUID,
    team: StatelessTeam
): StatelessForumChannel = BlankStatelessForumChannel(client, id, team)

internal data class BlankStatelessForumThread(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val team: StatelessTeam,
    override val channel: StatelessForumChannel
): StatelessForumThread

public fun StatelessForumThread(
    client: DeckClient,
    id: IntGenericId,
    team: StatelessTeam,
    channel: StatelessForumChannel
): StatelessForumThread = BlankStatelessForumThread(client, id, team, channel)

internal data class BlankStatelessTeam(
    override val client: DeckClient,
    override val id: GenericId
): StatelessTeam

internal data class InvalidStatelessTeam(override val client: DeckClient): StatelessTeam {
    override val id: GenericId get() = error("You can't obtain an invalid team's id nor state. If you're getting this error, the reason is probably because the team property you're trying to access was not specified by guilded's API, and this is just an shell.")
}

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

internal data class BlankStatelessSchedulingChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val team: StatelessTeam
): StatelessSchedulingChannel

public fun StatelessSchedulingChannel(
    client: DeckClient,
    id: UUID,
    team: StatelessTeam
): StatelessSchedulingChannel = BlankStatelessSchedulingChannel(client, id, team)

internal data class BlankStatelessScheduleAvailability(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val team: StatelessTeam,
    override val channel: StatelessSchedulingChannel
): StatelessScheduleAvailability

public fun StatelessScheduleAvailability(
    client: DeckClient,
    id: IntGenericId,
    team: StatelessTeam,
    channel: StatelessSchedulingChannel
): StatelessScheduleAvailability = BlankStatelessScheduleAvailability(client, id, team, channel)