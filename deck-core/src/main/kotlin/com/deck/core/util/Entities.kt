package com.deck.core.util

import com.deck.common.content.EmbedBuilder
import com.deck.common.content.embedBuilder
import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.entity.Message
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessMessageChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

public suspend fun StatelessMessageChannel.sendMessage(text: String): Message = sendMessage {
    content {
        + text
    }
}

public suspend fun StatelessMessageChannel.sendEmbed(
    additionalText: String? = null,
    builder: EmbedBuilder.() -> Unit
): Message = sendMessage {
    content {
        if (additionalText != null)
            + additionalText
        + embedBuilder(builder)
    }
}

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
