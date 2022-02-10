package com.deck.core.util

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.DeckClient
import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.stateless.channel.StatelessForumThread
import com.deck.core.stateless.channel.StatelessMessageChannel
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