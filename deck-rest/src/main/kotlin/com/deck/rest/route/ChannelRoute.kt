package com.deck.rest.route

import com.deck.common.entity.RawChannel
import com.deck.common.entity.RawChannelAvailability
import com.deck.common.entity.RawChannelForumThread
import com.deck.common.entity.RawMessage
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.mapToModel
import com.deck.rest.RestClient
import com.deck.rest.builder.CreateForumThreadBuilder
import com.deck.rest.builder.CreateForumThreadReplyBuilder
import com.deck.rest.builder.CreateScheduleAvailabilityBuilder
import com.deck.rest.builder.SendMessageRequestBuilder
import com.deck.rest.request.*
import com.deck.rest.util.Route
import io.ktor.http.*
import java.util.*

public class ChannelRoute(client: RestClient) : Route(client) {
    public suspend fun getChannel(channelId: UUID): RawChannel = sendRequest<RawChannel, Unit>(
        endpoint = "/channels/$channelId/chat",
        method = HttpMethod.Get
    )

    public suspend fun sendMessage(
        channelId: UUID,
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

    public suspend fun retrieveForumThreads(
        channelId: UUID
    ): List<RawChannelForumThread> = sendRequest<GetForumThreadsResponse, Unit>(
        endpoint = "/channels/$channelId/forums",
        method = HttpMethod.Get
    ).threads

    public suspend fun lockForumThread(
        channelId: UUID,
        threadId: IntGenericId
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/forums/$threadId/lock",
        method = HttpMethod.Put
    )

    public suspend fun pinForumThread(
        channelId: UUID,
        threadId: IntGenericId,
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/forums/$threadId/sticky",
        method = HttpMethod.Put
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

    public suspend fun getMessage(channelId: UUID, messageId: UUID): GetMessageMetadata =
        sendRequest<GetMessageResponse, Unit>(
            endpoint = "/content/route/metadata?route=//channels/$channelId/chat?messageId=$messageId",
            method = HttpMethod.Get
        ).metadata

    public suspend fun deleteChannel(teamId: GenericId, channelId: UUID, groupId: GenericId? = null): Unit =
        sendRequest<Unit, Unit>(
            endpoint = "/teams/$teamId/groups/${groupId ?: "undefined"}/channels/$channelId",
            method = HttpMethod.Delete
        )

    public suspend fun getChannelMessages(channelId: UUID): List<RawMessage> =
        sendRequest<GetChannelMessagesResponse, Unit>(
            endpoint = "/channels/$channelId/messages",
            method = HttpMethod.Get
        ).messages

    public suspend fun deleteMessage(channelId: UUID, messageId: UUID): Unit = sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/messages/$messageId",
        method = HttpMethod.Delete
    )

    public suspend fun addReaction(channelId: UUID, messageId: UUID, reactionId: IntGenericId): Unit =
        sendRequest<Unit, Unit>(
            endpoint = "/channels/$channelId/messages/$messageId/reactions/$reactionId",
            method = HttpMethod.Post
        )

    public suspend fun deleteOwnReaction(channelId: UUID, messageId: UUID, reactionId: IntGenericId): Unit =
        sendRequest<Unit, Unit>(
            endpoint = "/channels/$channelId/messages/$messageId/reactions/$reactionId",
            method = HttpMethod.Delete
        )

    public suspend fun getPinnedMessages(channelId: UUID): List<RawMessage> =
        sendRequest<GetChannelMessagesResponse, Unit>(
            endpoint = "/channels/$channelId/pins",
            method = HttpMethod.Get
        ).messages

    public suspend fun pinMessage(channelId: UUID, messageId: UUID): Unit =
        sendRequest(
            endpoint = "/channels/$channelId/pins",
            method = HttpMethod.Post,
            body = PinMessageRequest(messageId.mapToModel())
        )

    public suspend fun unpinMessage(channelId: UUID, messageId: UUID): Unit = sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/pins/$messageId",
        method = HttpMethod.Delete
    )

    public suspend fun leaveThread(channelId: UUID): String = sendRequest<String, Unit>(
        endpoint = "/users/${client.selfId}/channels/$channelId",
        method = HttpMethod.Delete
    )

    public suspend fun archiveThread(teamId: GenericId, threadId: UUID, groupId: GenericId? = null): Unit =
        sendRequest<Unit, Unit>(
            endpoint = "/teams/$teamId/groups/${groupId ?: "undefined"}/channels/$threadId/archive",
            method = HttpMethod.Put
        )

    public suspend fun restoreThread(teamId: GenericId, threadId: UUID, groupId: GenericId? = null): String =
        sendRequest<String, Unit>(
            endpoint = "/teams/$teamId/groups/${groupId ?: "undefined"}/channels/$threadId/restore",
            method = HttpMethod.Put
        )

    public suspend fun createAvailability(
        channelId: UUID,
        builder: CreateScheduleAvailabilityBuilder.() -> Unit
    ): Pair<IntGenericId, List<RawChannelAvailability>> = sendRequest<CreateScheduleAvailabilityResponse, CreateScheduleAvailabilityRequest>(
        endpoint = "/channels/$channelId/availability",
        method = HttpMethod.Post,
        body = CreateScheduleAvailabilityBuilder().apply(builder).toRequest()
    ).let { response -> response.id to response.availabilities }

    public suspend fun retrieveAvailabilities(channelId: UUID): List<RawChannelAvailability> = sendRequest<List<RawChannelAvailability>, Unit>(
        endpoint = "channels/$channelId/availability",
        method = HttpMethod.Get
    )

    public suspend fun updateAvailability(
        channelId: UUID,
        availabilityId: IntGenericId,
        builder: CreateScheduleAvailabilityBuilder.() -> Unit
    ): Pair<IntGenericId, List<RawChannelAvailability>> = sendRequest<CreateScheduleAvailabilityResponse, CreateScheduleAvailabilityRequest>(
        endpoint = "/channels/$channelId/availability/$availabilityId",
        method = HttpMethod.Put,
        body = CreateScheduleAvailabilityBuilder().apply(builder).toRequest()
    ).let { response -> response.id to response.availabilities }

    public suspend fun deleteAvailability(
        channelId: UUID,
        availabilityId: IntGenericId
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/channels/$channelId/availability/$availabilityId",
        method = HttpMethod.Delete,
    )
}