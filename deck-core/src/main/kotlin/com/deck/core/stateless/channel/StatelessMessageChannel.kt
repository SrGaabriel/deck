package com.deck.core.stateless.channel

import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessServer
import java.util.*

public interface StatelessMessageChannel: StatelessEntity {
    public val id: UUID
    public val server: StatelessServer?
}