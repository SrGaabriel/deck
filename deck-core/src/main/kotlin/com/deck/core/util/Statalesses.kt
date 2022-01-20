package com.deck.core.util

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessMessageChannel
import java.util.*

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
