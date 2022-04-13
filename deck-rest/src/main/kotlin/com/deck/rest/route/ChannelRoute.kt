package com.deck.rest.route

import com.deck.common.entity.RawDocumentation
import com.deck.common.entity.RawForumThread
import com.deck.common.entity.RawListItem
import com.deck.common.entity.RawMessage
import com.deck.common.util.DeckObsoleteApi
import com.deck.common.util.IntGenericId
import com.deck.rest.RestClient
import com.deck.rest.builder.CreateDocumentationRequestBuilder
import com.deck.rest.builder.CreateForumThreadRequestBuilder
import com.deck.rest.builder.CreateListItemRequestBuilder
import com.deck.rest.builder.SendMessageRequestBuilder
import com.deck.rest.request.*
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
    ): RawMessage = sendRequest<SendMessageResponse, UUID>(
        endpoint = "/channels/$channelId/messages/$messageId",
        method = HttpMethod.Get
    ).message

    public suspend fun getChannelMessages(
        channelId: UUID,
        includePrivate: Boolean = false
    ): List<RawMessage> = sendRequest<GetChannelMessagesResponse, Unit>(
        endpoint = "/channels/$channelId/messages?includePrivate=$includePrivate",
        method = HttpMethod.Get,
    ).messages

    public suspend fun addReactionToContent(
        channelId: UUID,
        messageId: UUID,
        emoteId: IntGenericId
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/content/$messageId/emotes/$emoteId",
        method = HttpMethod.Put
    )

    @DeckObsoleteApi
    // Not yet supported
    public suspend fun removeReactionFromContent(
        channelId: UUID,
        messageId: UUID,
        emoteId: IntGenericId
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/content/$messageId/emotes/$emoteId",
        method = HttpMethod.Delete
    )

    public suspend fun createDocumentation(
        channelId: UUID,
        builder: CreateDocumentationRequestBuilder.() -> Unit
    ): RawDocumentation = sendRequest<CreateDocumentationResponse, CreateDocumentationRequest>(
        endpoint = "/channels/$channelId/docs",
        method = HttpMethod.Post,
        body = CreateDocumentationRequestBuilder().apply(builder).toRequest()
    ).documentation

    public suspend fun getDocumentation(
        channelId: UUID,
        documentationId: IntGenericId
    ): RawDocumentation = sendRequest<CreateDocumentationResponse, Unit>(
        endpoint = "/channels/$channelId/docs/$documentationId",
        method = HttpMethod.Get
    ).documentation

    public suspend fun getDocumentations(
        channelId: UUID
    ): List<RawDocumentation> = sendRequest<GetDocumentationsResponse, Unit>(
        endpoint = "/channels/$channelId/docs",
        method = HttpMethod.Get
    ).documentations

    public suspend fun updateDocumentation(
        channelId: UUID,
        documentationId: IntGenericId,
        builder: CreateDocumentationRequestBuilder.() -> Unit
    ): RawDocumentation = sendRequest<CreateDocumentationResponse, CreateDocumentationRequest>(
        endpoint = "/channels/$channelId/docs/$documentationId",
        method = HttpMethod.Put,
        body = CreateDocumentationRequestBuilder().apply(builder).toRequest()
    ).documentation

    public suspend fun deleteDocumentation(
        channelId: UUID,
        documentationId: IntGenericId
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/docs/$documentationId",
        method = HttpMethod.Delete
    )

    public suspend fun createListItem(
        channelId: UUID,
        builder: CreateListItemRequestBuilder.() -> Unit
    ): RawListItem = sendRequest<CreateListItemResponse, CreateListItemRequest>(
        endpoint = "/channels/$channelId/items",
        method = HttpMethod.Post,
        body = CreateListItemRequestBuilder().apply(builder).toRequest()
    ).listItem

    public suspend fun createForumThread(
        channelId: UUID,
        builder: CreateForumThreadRequestBuilder.() -> Unit
    ): RawForumThread = sendRequest<CreateForumThreadResponse, CreateForumThreadRequest>(
        endpoint = "/channels/$channelId/forum",
        method = HttpMethod.Post,
        body = CreateForumThreadRequestBuilder().apply(builder).toRequest()
    ).forumThread
}