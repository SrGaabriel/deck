package com.deck.core.stateless

import com.deck.core.entity.Message
import com.deck.core.stateless.channel.StatelessMessageChannel
import java.util.*

public interface StatelessMessage: StatelessEntity {
    public val id: UUID
    public val channel: StatelessMessageChannel

    public suspend fun update(content: String): Message =
        channel.updateMessage(id, content)

    public suspend fun delete(): Unit =
        channel.deleteMessage(id)
}