package com.deck.core.util

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.stateless.*
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
    override val teamId: GenericId? = null
): StatelessMessageChannel

internal class BlankStatelessTeam(
    override val client: DeckClient,
    override val id: GenericId
): StatelessTeam

internal class BlankStatelessUser(
    override val client: DeckClient,
    override val id: GenericId
): StatelessUser
