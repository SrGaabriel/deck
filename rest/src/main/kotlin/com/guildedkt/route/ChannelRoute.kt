package com.guildedkt.route

import com.guildedkt.RestClient
import com.guildedkt.builder.SendMessageRequestBuilder
import com.guildedkt.request.SendMessageRequest
import com.guildedkt.request.SendMessageResponse
import com.guildedkt.util.Route
import com.guildedkt.util.UniqueId
import io.ktor.http.*

class ChannelRoute(client: RestClient): Route(client) {
    suspend fun sendMessage(channelId: UniqueId, builder: SendMessageRequestBuilder.() -> Unit) = sendRequest<SendMessageResponse, SendMessageRequest>(
        endpoint = "/channels/${channelId.raw}/messages",
        method = HttpMethod.Post,
        body = SendMessageRequestBuilder().apply(builder).toRequest()
    ).message
}