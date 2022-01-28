package com.deck.common.entity

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class RawMessage(
    val id: UniqueId,
    val channelId: UniqueId,
    val createdBy: GenericId,
    val content: RawMessageContent,
    val createdAt: Instant,
    val editedAt: Instant?,
    val deletedAt: Instant?,
    val reactions: OptionalProperty<List<RawReaction>> = OptionalProperty.NotPresent,
    val isPinned: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val pinnedBy: OptionalProperty<GenericId?> = OptionalProperty.NotPresent,
    val webhookId: GenericId?,
    val botId: OptionalProperty<GenericId?> = OptionalProperty.NotPresent,
    val type: String
)

@Serializable
public data class RawPartialSentMessage(
    val id: UniqueId,
    val createdAt: Instant,
    val content: RawMessageContent,
    val type: String,
    val createdBy: GenericId,
    val isPrivate: Boolean,
    val repliesToIds: List<UniqueId>
) {
    @Transient
    var isSilent: Boolean = false
}

@Serializable
public data class RawPartialRepliedMessage(
    val id: UniqueId,
    val content: RawMessageContent,
    val type: String,
    val createdBy: GenericId,
    val createdAt: Instant,
    val editedAt: Instant?,
    val deletedAt: Instant?,
    val channelId: UniqueId,
    val webhookId: GenericId?,
    val isSilent: Boolean,
    val isPrivate: Boolean
)

@Serializable
public data class RawPartialDeletedMessage(
    val id: UniqueId,
    val channelId: UniqueId,
    val createdAt: Instant
)

@Serializable
public data class RawMessageContent(
    @SerialName("object") val contentObject: String,
    val document: RawMessageContentNode
)

@Serializable
public data class RawMessageContentNode(
    @SerialName("object") val documentObject: String,
    val data: RawMessageContentData = RawMessageContentData(),
    val type: RawMessageContentNodeType = RawMessageContentNodeType.BLANK,
    val nodes: List<RawMessageContentNode> = emptyList(),
    val leaves: OptionalProperty<List<RawMessageContentNodeLeaves>> = OptionalProperty.NotPresent
)

@Serializable
public enum class RawMessageContentNodeType {
    BLANK,
    @SerialName("paragraph")
    PARAGRAPH,
    @SerialName("link")
    LINK,
    @SerialName("reaction")
    REACTION,
    @SerialName("webhookMessage")
    WEBHOOK_MESSAGE,
    @SerialName("image")
    IMAGE,
    @SerialName("systemMessage")
    SYSTEM_MESSAGE,
    @SerialName("block-quote-line")
    BLOCK_QUOTE_LINE,
    @SerialName("block-quote-container")
    BLOCK_QUOTE_CONTAINER,
    @SerialName("markdown-plain-text")
    MARKDOWN_PLAIN_TEXT,
    @SerialName("code-container")
    CODE_BLOCK,
    @SerialName("code-line")
    CODE_LINE,
    @SerialName("unordered-list")
    BULLETED_LIST,
    @SerialName("ordered-list")
    NUMBERED_LIST,
    @SerialName("list-item")
    LIST_ITEM,
    @SerialName("replying-to-user-header")
    REPLYING_TO_USER_HEADER;
}

@Serializable
public data class RawMessageContentData(
    val githubDeliveryId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val embeds: OptionalProperty<List<RawEmbed>> = OptionalProperty.NotPresent,
    val src: OptionalProperty<String> = OptionalProperty.NotPresent,
    val href: OptionalProperty<String> = OptionalProperty.NotPresent,
    val language: OptionalProperty<String> = OptionalProperty.NotPresent,
    val isList: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val reaction: OptionalProperty<RawReaction> = OptionalProperty.NotPresent,
    val postId: OptionalProperty<IntGenericId> = OptionalProperty.NotPresent,
    val type: OptionalProperty<String> = OptionalProperty.NotPresent,
    val createdBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent
)

@Serializable
public data class RawMessageContentNodeLeaves(
    @SerialName("object") val leavesObject: String,
    val text: String,
    val marks: List<RawMessageContentNodeLeavesMark>
)

/**
 * Here we'll create a new object for the marks, because
 * we don't want our mark type to conflict with the [RawMessageContentNodeType] enum.
 */
@Serializable
public data class RawMessageContentNodeLeavesMark(
    @SerialName("object") val markObject: String,
    val type: RawMessageContentNodeLeavesMarkType,
    val data: RawMessageContentData
)

@Serializable
public enum class RawMessageContentNodeLeavesMarkType {
    @SerialName("underline")
    UNDERLINE,
    @SerialName("bold")
    BOLD,
    @SerialName("italic")
    ITALIC,
    @SerialName("strikethrough")
    STRIKETHROUGH,
    @SerialName("spoiler")
    SPOILER,
    @SerialName("inline-code-v2")
    INLINE_CODE
}

@Serializable
public data class RawSlowmode(
    val channelCooldown: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val cooldownRemaining: OptionalProperty<Int> = OptionalProperty.NotPresent
)

@Serializable
public data class RawMessageMentionedUserInfo(
    val isDirectlyMentioned: Boolean,
    val mentionedByName: Boolean,
    val mentionedByRole: Boolean,
    val mentionedByEveryone: Boolean,
    val mentionedByHere: Boolean
)