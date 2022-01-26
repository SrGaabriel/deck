package com.deck.core.stateless

import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.entity.Message
import com.deck.core.stateless.channel.StatelessMessageChannel
import java.util.*

public interface StatelessMessage: StatelessEntity<Message> {
    public val id: UUID
    public val channel: StatelessMessageChannel

    public suspend fun sendReply(builder: DeckMessageBuilder.() -> Unit): Message = channel.sendMessage {
        this.let(builder)
        this.repliesTo = this@StatelessMessage.id
    }

    override suspend fun getState(): Message {
        TODO("Not yet implemented")
    }
}