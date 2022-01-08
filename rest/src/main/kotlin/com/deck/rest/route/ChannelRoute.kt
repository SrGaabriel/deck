package com.deck.rest.route

import com.deck.common.util.UniqueId
import com.deck.rest.RestClient
import com.deck.rest.builder.SendMessageRequestBuilder
import com.deck.rest.request.SendMessageRequest
import com.deck.rest.request.SendMessageResponse
import com.deck.rest.util.Route
import io.ktor.http.*

class ChannelRoute(client: RestClient): Route(client) {
    suspend fun sendMessage(channelId: UniqueId, builder: SendMessageRequestBuilder.() -> Unit) = sendRequest<SendMessageResponse, SendMessageRequest>(
        endpoint = "/channels/${channelId.raw}/messages",
        method = HttpMethod.Post,
        body = SendMessageRequestBuilder().apply(builder).toRequest()
    )
}