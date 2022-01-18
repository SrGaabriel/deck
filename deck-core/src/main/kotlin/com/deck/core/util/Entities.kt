package com.deck.core.util

import com.deck.common.util.GenericId
import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.entity.Channel
import com.deck.core.entity.Message
import com.deck.core.entity.TeamChannel

val Channel.teamId: GenericId? get() = when (this) {
    is TeamChannel -> teamId
    else -> null
}

suspend fun Channel.sendMessage(builder: DeckMessageBuilder.() -> Unit): Message =
    client.entityStrategizer.decodePartialSentMessage(
        id,
        teamId,
        client.rest.channelRoute.sendMessage(this, builder).message
    )
