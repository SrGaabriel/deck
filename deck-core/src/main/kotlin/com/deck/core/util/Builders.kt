package com.deck.core.util

import com.deck.core.builder.DeckMessageBuilder
import com.deck.rest.request.SendMessageResponse
import com.deck.rest.route.ChannelRoute
import io.ktor.http.*
import java.util.*

public suspend fun ChannelRoute.sendMessage(
    id: UUID,
    builder: DeckMessageBuilder.() -> Unit
): SendMessageResponse =
    sendRequest(
        endpoint = "/channels/$id/messages",
        method = HttpMethod.Post,
        body = DeckMessageBuilder().apply(builder).toRequest()
    )