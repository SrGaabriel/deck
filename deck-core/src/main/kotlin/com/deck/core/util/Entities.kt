package com.deck.core.util

import com.deck.core.entity.Message
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.channel.StatelessMessageChannel

public suspend fun StatelessMessage.sendReply(content: String): Message = sendReply {
    this.content = content
}

public suspend fun StatelessMessageChannel.sendMessage(content: String): Message = sendMessage {
    this.content = content
}