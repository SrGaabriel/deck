package com.deck.rest.route

import com.deck.common.entity.RawChannel
import com.deck.common.util.UniqueId
import com.deck.rest.RestClient
import com.deck.rest.builder.SendMessageRequestBuilder
import com.deck.rest.request.SendMessageRequest
import com.deck.rest.request.SendMessageResponse
import com.deck.rest.util.Route
import io.ktor.http.*

public class ChannelRoute(client: RestClient) : Route(client) {
    public suspend fun getChannel(channelId: UniqueId): RawChannel = sendRequest<RawChannel, Unit>(
        endpoint = "/channels/$channelId/chat",
        method = HttpMethod.Get
    )

    public suspend fun sendMessage(
        channelId: UniqueId,
        builder: SendMessageRequestBuilder.() -> Unit
    ): SendMessageResponse {
        val request = SendMessageRequestBuilder().apply(builder).toRequest()
        val response = sendRequest<SendMessageResponse, SendMessageRequest>(
            endpoint = "/channels/$channelId/messages",
            method = HttpMethod.Post,
            body = request
        )
        response.message.isSilent = request.isSilent
        return response
    }
}
