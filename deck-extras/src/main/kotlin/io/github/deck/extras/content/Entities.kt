package io.github.deck.extras.content

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.common.util.OptionalProperty
import io.github.deck.common.util.UniqueId
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive

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
    @SerialName("mention")
    MENTION,
    @SerialName("channel")
    MENTION_CHANNEL
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
    val createdBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val mention: OptionalProperty<RawMentionData> = OptionalProperty.NotPresent,
    val channel: OptionalProperty<RawMentionedChannel> = OptionalProperty.NotPresent
)

@Serializable
public data class RawMentionData(
    val type: RawMentionType,
    val matcher: OptionalProperty<String> = OptionalProperty.NotPresent,
    val name: OptionalProperty<String> = OptionalProperty.NotPresent,
    val id: JsonPrimitive,
    val color: OptionalProperty<String> = OptionalProperty.NotPresent,
    @SerialName("nickname")
    val hasNickname: OptionalProperty<Boolean> = OptionalProperty.NotPresent
)

@Serializable
public data class RawMentionedChannel(
    public val id: UniqueId
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

@Serializable
public enum class RawMentionType {
    @SerialName("person") USER,
    @SerialName("role") ROLE;
}

@Serializable
public enum class RawMessageNodeReplyToUserHeaderType(public val serialName: String) {
    QUOTE("block-quote"),
    REFERENCE("reply");
}

@Serializable
public data class RawEmbed(
    val title: OptionalProperty<String> = OptionalProperty.NotPresent,
    val description: OptionalProperty<String> = OptionalProperty.NotPresent,
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    val timestamp: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val color: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val footer: OptionalProperty<RawEmbedFooter> = OptionalProperty.NotPresent,
    val image: OptionalProperty<RawEmbedImage> = OptionalProperty.NotPresent,
    val thumbnail: OptionalProperty<RawEmbedImage> = OptionalProperty.NotPresent,
    val video: OptionalProperty<RawEmbedImage> = OptionalProperty.NotPresent,
    val provider: OptionalProperty<RawEmbedProvider> = OptionalProperty.NotPresent,
    val author: OptionalProperty<RawEmbedAuthor> = OptionalProperty.NotPresent,
    val fields: OptionalProperty<List<RawEmbedField>> = OptionalProperty.NotPresent
)

@Serializable
public data class RawEmbedAuthor(
    val name: OptionalProperty<String> = OptionalProperty.NotPresent,
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    val iconUrl: OptionalProperty<String> = OptionalProperty.NotPresent,
)

@Serializable
public data class RawEmbedProvider(
    val name: OptionalProperty<String> = OptionalProperty.NotPresent,
    val url: OptionalProperty<String> = OptionalProperty.NotPresent
)

@Serializable
public data class RawEmbedImage(
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    val height: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val width: OptionalProperty<Int> = OptionalProperty.NotPresent
)

@Serializable
public data class RawEmbedField(
    val name: String,
    val value: String,
    val inline: OptionalProperty<Boolean> = OptionalProperty.NotPresent
)

@Serializable
public data class RawEmbedFooter(
    val text: String,
    val iconUrl: OptionalProperty<String> = OptionalProperty.NotPresent
)

@Serializable
public data class RawReaction(
    val id: OptionalProperty<IntGenericId> = OptionalProperty.NotPresent,
    val customReaction: OptionalProperty<RawCustomReaction> = OptionalProperty.NotPresent,
    val customReactionId: IntGenericId,
    val createdBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent
)

@Serializable
public data class RawCustomReaction(
    val id: IntGenericId,
    val name: String,
    val png: String?,
    val webp: String?,
    val apgn: OptionalProperty<String?> = OptionalProperty.NotPresent
)