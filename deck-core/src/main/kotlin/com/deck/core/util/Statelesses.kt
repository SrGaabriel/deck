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

internal class BlankStatelessMember(
    override val client: DeckClient,
    override val id: GenericId,
): StatelessMember

internal class BlankStatelessMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val channel: StatelessMessageChannel
): StatelessMessage

internal class BlankStatelessMessageChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val team: StatelessTeam?
): StatelessMessageChannel

public class BlankStatelessForumChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val team: StatelessTeam
): StatelessForumChannel

public class BlankStatelessForumThread(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val team: StatelessTeam,
    override val channel: StatelessForumChannel
): StatelessForumThread

internal class BlankStatelessTeam(
    override val client: DeckClient,
    override val id: GenericId
): StatelessTeam

internal class BlankStatelessUser(
    override val client: DeckClient,
    override val id: GenericId
): StatelessUser