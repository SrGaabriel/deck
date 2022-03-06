package com.deck.rest.request

import com.deck.common.entity.RawDocumentation
import com.deck.common.entity.RawForumThread
import com.deck.common.entity.RawListItem
import com.deck.common.entity.RawMessage
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class SendMessageRequest(
    public val content: String,
    public val isPrivate: Boolean = false,
    public val replyMessageIds: List<UniqueId> = emptyList()
)

@Serializable
public data class SendMessageResponse(
    public val message: RawMessage
)

@Serializable
public data class UpdateMessageRequest(
    public val content: String
)

@Serializable
public data class GetChannelMessagesResponse(
    public val messages: List<RawMessage>
)

@Serializable
public data class CreateDocumentationRequest(
    public val title: String,
    public val content: String
)

@Serializable
public data class CreateDocumentationResponse(
    @SerialName("doc")
    public val documentation: RawDocumentation
)

@Serializable
public data class GetDocumentationsResponse(
    @SerialName("docs")
    public val documentations: List<RawDocumentation>
)

@Serializable
public data class CreateListItemRequest(
    public val message: String,
    public val note: OptionalProperty<String> = OptionalProperty.NotPresent
)

@Serializable
public data class CreateListItemResponse(
    public val listItem: RawListItem
)

@Serializable
public data class CreateForumThreadRequest(
    val title: String,
    val content: String
)

@Serializable
public data class CreateForumThreadResponse(
    val forumThread: RawForumThread
)