package com.deck.rest.route

import com.deck.common.entity.RawChannel
import com.deck.common.entity.RawChannelForumThread
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.UniqueId
import com.deck.rest.RestClient
import com.deck.rest.builder.CreateForumThreadBuilder
import com.deck.rest.builder.CreateForumThreadReplyBuilder
import com.deck.rest.builder.SendMessageRequestBuilder
import com.deck.rest.request.*
import com.deck.rest.util.Route
import io.ktor.http.*
import java.util.*

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

    public suspend fun createForumThread(
        channelId: UUID,
        builder: CreateForumThreadBuilder.() -> Unit
    ): RawChannelForumThread = sendRequest(
        endpoint = "/channels/$channelId/forums",
        method = HttpMethod.Post,
        body = CreateForumThreadBuilder().apply(builder).toRequest()
    )

    public suspend fun updateForumThread(
        channelId: UUID,
        threadId: IntGenericId,
        builder: CreateForumThreadBuilder.() -> Unit
    ): Unit = sendRequest(
        endpoint = "/channels/$channelId/forums/$threadId",
        method = HttpMethod.Put,
        body = CreateForumThreadBuilder().apply(builder).toRequest()
    )

    public suspend fun deleteForumThread(
        channelId: UUID,
        threadId: IntGenericId
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/forums/$threadId",
        method = HttpMethod.Delete
    )

    public suspend fun createForumThreadReply(
        channelId: UUID,
        threadId: IntGenericId,
        builder: CreateForumThreadReplyBuilder.() -> Unit
    ): CreateForumThreadReplyResponse = sendRequest(
        endpoint = "/channels/$channelId/forums/$threadId/replies",
        method = HttpMethod.Post,
        body = CreateForumThreadReplyBuilder().apply(builder).toRequest()
    )

    public suspend fun addReactionToForumThreadReply(
        teamId: GenericId,
        replyId: IntGenericId,
        reactionId: IntGenericId
    ): Unit = sendRequest(
        endpoint = "/reactions/reply/$replyId/undefined?reactionPack=Smilies",
        method = HttpMethod.Put,
        body = AddForumThreadReplyReactionRequest(reactionId, teamId)
    )

    public suspend fun removeReactionFromForumThreadReply(
        teamId: GenericId,
        replyId: IntGenericId,
        reactionId: IntGenericId
    ): Unit = sendRequest(
        endpoint = "/reactions/reply/$replyId/undefined",
        method = HttpMethod.Delete,
        body = AddForumThreadReplyReactionRequest(reactionId, teamId)
    )

    public suspend fun getMessage(channelId: UniqueId, messageId: UniqueId): GetMessageResponse =
        sendRequest<GetMessageResponse, Unit>(
            endpoint = "/content/route/metadata?route=//channels/$channelId/chat?messageId=$messageId",
            method = HttpMethod.Get
        )

    public suspend fun deleteChannel(teamId: GenericId, channelId: UniqueId, groupId: GenericId? = null): Unit =
        sendRequest<Unit, Unit>(
            endpoint = "/teams/$teamId/groups/${groupId ?: "undefined"}/channels/$channelId",
            method = HttpMethod.Delete
        )

    public suspend fun getChannelMessages(channelId: UniqueId): GetChannelMessagesResponse =
        sendRequest<GetChannelMessagesResponse, Unit>(
            endpoint = "/channels/$channelId/messages",
            method = HttpMethod.Get
        )

    public suspend fun deleteMessage(channelId: UniqueId, messageId: UniqueId): Unit = sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/messages/$messageId",
        method = HttpMethod.Delete
    )

    public suspend fun addReaction(channelId: UniqueId, messageId: UniqueId, reactionId: IntGenericId): Unit =
        sendRequest<Unit, Unit>(
            endpoint = "/channels/$channelId/messages/$messageId/reactions/$reactionId",
            method = HttpMethod.Post
        )

    public suspend fun deleteOwnReaction(channelId: UniqueId, messageId: UniqueId, reactionId: IntGenericId): Unit =
        sendRequest<Unit, Unit>(
            endpoint = "/channels/$channelId/messages/$messageId/reactions/$reactionId",
            method = HttpMethod.Delete
        )

    public suspend fun getPinnedMessages(channelId: UniqueId): GetChannelMessagesResponse =
        sendRequest<GetChannelMessagesResponse, Unit>(
            endpoint = "/channels/$channelId/pins",
            method = HttpMethod.Get
        )

    public suspend fun pinMessage(channelId: UniqueId, messageId: UniqueId): Unit =
        sendRequest<Unit, PinMessageRequest>(
            endpoint = "/channels/$channelId/pins",
            method = HttpMethod.Post,
            body = PinMessageRequest(messageId)
        )

    public suspend fun unpinMessage(channelId: UniqueId, messageId: UniqueId): Unit = sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/pins/$messageId",
        method = HttpMethod.Delete
    )

    public suspend fun leaveThread(userId: GenericId, channelId: UniqueId): String = sendRequest<String, Unit>(
        endpoint = "/users/$userId/channels/$channelId",
        method = HttpMethod.Delete
    )

    public suspend fun archiveThread(teamId: GenericId, threadId: UniqueId, groupId: GenericId? = null): Unit =
        sendRequest<Unit, Unit>(
            endpoint = "/teams/$teamId/groups/${groupId ?: "undefined"}/channels/$threadId/archive",
            method = HttpMethod.Put
        )

    public suspend fun restoreThread(teamId: GenericId, threadId: UniqueId, groupId: GenericId? = null): String =
        sendRequest<String, Unit>(
            endpoint = "/teams/$teamId/groups/${groupId ?: "undefined"}/channels/$threadId/restore",
            method = HttpMethod.Put
        )
}