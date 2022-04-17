package io.github.deck.rest.route

import io.github.deck.common.entity.RawDocumentation
import io.github.deck.common.entity.RawForumThread
import io.github.deck.common.entity.RawListItem
import io.github.deck.common.entity.RawMessage
import io.github.deck.common.util.DeckUnsupported
import io.github.deck.common.util.IntGenericId
import io.github.deck.rest.RestClient
import io.github.deck.rest.builder.*
import io.github.deck.rest.request.*
import io.github.deck.rest.util.sendRequest
import io.ktor.http.*
import java.util.*

public class ChannelRoute(private val client: RestClient) {
    public suspend fun sendMessage(
        channelId: UUID,
        builder: SendMessageRequestBuilder.() -> Unit
    ): RawMessage = client.sendRequest<SendMessageResponse, SendMessageRequest>(
        endpoint = "/channels/$channelId/messages",
        method = HttpMethod.Post,
        body = SendMessageRequestBuilder().apply(builder).toRequest()
    ).message

    public suspend fun updateMessage(
        channelId: UUID,
        messageId: UUID,
        content: String
    ): RawMessage = client.sendRequest<SendMessageResponse, UpdateMessageRequest>(
        endpoint = "/channels/$channelId/messages/$messageId",
        method = HttpMethod.Put,
        body = UpdateMessageRequest(content)
    ).message

    public suspend fun deleteMessage(
        channelId: UUID,
        messageId: UUID,
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/messages/$messageId",
        method = HttpMethod.Delete,
    )

    public suspend fun getMessage(
        channelId: UUID,
        messageId: UUID
    ): RawMessage = client.sendRequest<SendMessageResponse, UUID>(
        endpoint = "/channels/$channelId/messages/$messageId",
        method = HttpMethod.Get
    ).message

    public suspend fun getChannelMessages(
        channelId: UUID,
        includePrivate: Boolean = false
    ): List<RawMessage> = client.sendRequest<GetChannelMessagesResponse, Unit>(
        endpoint = "/channels/$channelId/messages?includePrivate=$includePrivate",
        method = HttpMethod.Get,
    ).messages

    public suspend fun addReactionToContent(
        channelId: UUID,
        messageId: UUID,
        emoteId: IntGenericId
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/content/$messageId/emotes/$emoteId",
        method = HttpMethod.Put
    )

    @DeckUnsupported
    // Not yet supported
    public suspend fun removeReactionFromContent(
        channelId: UUID,
        messageId: UUID,
        emoteId: IntGenericId
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/content/$messageId/emotes/$emoteId",
        method = HttpMethod.Delete
    )

    public suspend fun createDocumentation(
        channelId: UUID,
        builder: CreateDocumentationRequestBuilder.() -> Unit
    ): RawDocumentation = client.sendRequest<CreateDocumentationResponse, CreateDocumentationRequest>(
        endpoint = "/channels/$channelId/docs",
        method = HttpMethod.Post,
        body = CreateDocumentationRequestBuilder().apply(builder).toRequest()
    ).documentation

    public suspend fun getDocumentation(
        channelId: UUID,
        documentationId: IntGenericId
    ): RawDocumentation = client.sendRequest<CreateDocumentationResponse, Unit>(
        endpoint = "/channels/$channelId/docs/$documentationId",
        method = HttpMethod.Get
    ).documentation

    public suspend fun getDocumentations(
        channelId: UUID
    ): List<RawDocumentation> = client.sendRequest<GetDocumentationsResponse, Unit>(
        endpoint = "/channels/$channelId/docs",
        method = HttpMethod.Get
    ).documentations

    public suspend fun updateDocumentation(
        channelId: UUID,
        documentationId: IntGenericId,
        builder: CreateDocumentationRequestBuilder.() -> Unit
    ): RawDocumentation = client.sendRequest<CreateDocumentationResponse, CreateDocumentationRequest>(
        endpoint = "/channels/$channelId/docs/$documentationId",
        method = HttpMethod.Put,
        body = CreateDocumentationRequestBuilder().apply(builder).toRequest()
    ).documentation

    public suspend fun deleteDocumentation(
        channelId: UUID,
        documentationId: IntGenericId
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/docs/$documentationId",
        method = HttpMethod.Delete
    )

    public suspend fun createListItem(
        channelId: UUID,
        builder: CreateListItemRequestBuilder.() -> Unit
    ): RawListItem = client.sendRequest<CreateListItemResponse, CreateListItemRequest>(
        endpoint = "/channels/$channelId/items",
        method = HttpMethod.Post,
        body = CreateListItemRequestBuilder().apply(builder).toRequest()
    ).listItem

    public suspend fun retrieveListItem(
        channelId: UUID,
        listItemId: UUID,
    ): RawListItem = client.sendRequest<CreateListItemResponse, Unit>(
        endpoint = "/channels/$channelId/items/$listItemId",
        method = HttpMethod.Get
    ).listItem

    public suspend fun retrieveListChannelItems(channelId: UUID): List<RawListItem> = client.sendRequest<GetListChannelItemsResponse, Unit>(
        endpoint = "/channels/$channelId/items",
        method = HttpMethod.Get
    ).listItems

    public suspend fun updateListItem(
        channelId: UUID,
        listItemId: UUID,
        builder: UpdateListItemRequestBuilder.() -> Unit
    ): RawListItem = client.sendRequest<CreateListItemResponse, CreateListItemRequest>(
        endpoint = "/channels/$channelId/items/$listItemId",
        method = HttpMethod.Put,
        body = UpdateListItemRequestBuilder().apply(builder).toRequest()
    ).listItem

    public suspend fun deleteListItem(
        channelId: UUID,
        listItemId: UUID
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/items/$listItemId",
        method = HttpMethod.Delete
    )

    public suspend fun createForumThread(
        channelId: UUID,
        builder: CreateForumThreadRequestBuilder.() -> Unit
    ): RawForumThread = client.sendRequest<CreateForumThreadResponse, CreateForumThreadRequest>(
        endpoint = "/channels/$channelId/forum",
        method = HttpMethod.Post,
        body = CreateForumThreadRequestBuilder().apply(builder).toRequest()
    ).forumThread
}