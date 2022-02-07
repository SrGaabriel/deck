package com.deck.rest.builder

import com.deck.common.content.Content
import com.deck.common.content.node.encode
import com.deck.common.entity.RawChannelContentType
import com.deck.common.util.IntGenericId
import com.deck.common.util.mapToModel
import com.deck.common.util.nullableOptional
import com.deck.rest.request.CreateForumThreadReplyRequest
import com.deck.rest.request.CreateForumThreadRequest
import com.deck.rest.request.CreateTeamChannelRequest
import com.deck.rest.request.SendMessageRequest
import java.util.*
import kotlin.properties.Delegates
import kotlin.random.Random

public class SendMessageRequestBuilder : RequestBuilder<SendMessageRequest> {
    public var uniqueId: UUID = UUID.randomUUID()
    public var private: Boolean = false
    public var silent: Boolean = false

    public val content: Content = Content()

    public var repliesTo: UUID? = null

    override fun toRequest(): SendMessageRequest = SendMessageRequest(
        messageId = uniqueId.mapToModel(),
        content = content.encode(),
        isPrivate = private,
        isSilent = silent,
        repliesToIds = listOfNotNull(repliesTo?.mapToModel())
    )
}

public class CreateTeamChannelBuilder: RequestBuilder<CreateTeamChannelRequest> {
    public var name: String by Delegates.notNull()
    public var type: RawChannelContentType = RawChannelContentType.Chat
    public var public: Boolean = false

    public var categoryId: IntGenericId? = null

    override fun toRequest(): CreateTeamChannelRequest = CreateTeamChannelRequest(
        name = name,
        contentType = type,
        isPublic = public,
        channelCategoryId = categoryId.nullableOptional()
    )
}

public class CreateForumThreadBuilder: RequestBuilder<CreateForumThreadRequest>, MutableContentHolder {
    public var id: IntGenericId = Random.nextInt(1000000000, 2000000000)
    public var title: String = ""
    override var content: Content = Content()

    override fun toRequest(): CreateForumThreadRequest = CreateForumThreadRequest(
        threadId = id,
        title = title,
        message = content.encode()
    )
}

public class CreateForumThreadReplyBuilder: RequestBuilder<CreateForumThreadReplyRequest>, MutableContentHolder {
    public var id: IntGenericId = Random.nextInt(1000000000, 2000000000)
    override var content: Content = Content()

    override fun toRequest(): CreateForumThreadReplyRequest = CreateForumThreadReplyRequest(
        id = id,
        message = content.encode()
    )
}