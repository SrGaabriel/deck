package com.deck.core.stateless

import com.deck.core.stateless.channel.StatelessMessageChannel
import java.util.*

public interface StatelessMessage: StatelessEntity {
    public val id: UUID
    public val channel: StatelessMessageChannel
}