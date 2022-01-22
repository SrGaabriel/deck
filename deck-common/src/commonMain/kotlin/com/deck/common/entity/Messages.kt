package com.deck.common.entity

import com.deck.common.util.*
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
    val contentType: RawChannelContentType,
    val isReply: Boolean,
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
    val type: OptionalProperty<String> = OptionalProperty.NotPresent,
    val nodes: List<RawMessageContentNode> = emptyList(),
    val leaves: OptionalProperty<List<RawMessageContentNodeLeaves>> = OptionalProperty.NotPresent
)

@Serializable
public data class RawMessageContentData(
    val githubDeliveryId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val embeds: OptionalProperty<List<RawEmbed>> = OptionalProperty.NotPresent,
    val src: OptionalProperty<String> = OptionalProperty.NotPresent,
    val href: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: OptionalProperty<RawReaction> = OptionalProperty.NotPresent
)

@Serializable
public data class RawMessageContentNodeLeaves(
    @SerialName("object") val leavesObject: String,
    val text: String,
    @DeckUnknown val marks: List<Unit>
)

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
