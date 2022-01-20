package com.deck.core.util

import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessMessageChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

public suspend fun StatelessMessageChannel.sendUnreifiedMessage(builder: DeckMessageBuilder.() -> Unit): StatelessMessage {
    coroutineScope {
        launch {
            sendMessage(builder)
        }
    }
    return BlankStatelessMessage(client, id, this)
}

public suspend fun StatelessMessage.sendUnreifiedReply(builder: DeckMessageBuilder.() -> Unit): StatelessMessage {
    coroutineScope {
        launch {
            sendReply(builder)
        }
    }
    return BlankStatelessMessage(client, id, this.channel)
}
