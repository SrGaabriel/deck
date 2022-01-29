package com.deck.rest.route

import com.deck.common.entity.RawMessage
import com.deck.rest.RestClient
import com.deck.rest.builder.SendMessageRequestBuilder
import com.deck.rest.request.GetChannelMessagesResponse
import com.deck.rest.request.SendMessageRequest
import com.deck.rest.request.SendMessageResponse
import com.deck.rest.request.UpdateMessageRequest
import com.deck.rest.util.Route
import io.ktor.http.*
import java.util.*

public class ChannelRoute(client: RestClient): Route(client) {
    public suspend fun sendMessage(
        channelId: UUID,
        builder: SendMessageRequestBuilder.() -> Unit
    ): RawMessage = sendRequest<SendMessageResponse, SendMessageRequest>(
        endpoint = "/channels/$channelId/messages",
        method = HttpMethod.Post,
        body = SendMessageRequestBuilder().apply(builder).toRequest()
    ).message

    public suspend fun updateMessage(
        channelId: UUID,
        messageId: UUID,
        content: String
    ): RawMessage = sendRequest<SendMessageResponse, UpdateMessageRequest>(
        endpoint = "/channels/$channelId/messages/$messageId",
        method = HttpMethod.Put,
        body = UpdateMessageRequest(content)
    ).message

    public suspend fun deleteMessage(
        channelId: UUID,
        messageId: UUID,
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/messages/$messageId",
        method = HttpMethod.Delete,
    )

    public suspend fun getMessage(
        channelId: UUID,
        messageId: UUID
    ): RawMessage? = sendNullableRequest<SendMessageResponse, UUID>(
        endpoint = "/channels/$channelId/messages/$messageId",
        method = HttpMethod.Get
    )?.message

    public suspend fun getChannelMessages(
        channelId: UUID,
        includePrivate: Boolean = false
    ): List<RawMessage> = sendRequest<GetChannelMessagesResponse, Unit>(
        endpoint = "/channels/$channelId/messages?includePrivate=$includePrivate",
        method = HttpMethod.Get,
    ).messages
}