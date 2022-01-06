package com.guildedkt.entity

import com.guildedkt.util.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RawMessage(
    val id: UniqueId,
    val channelId: UniqueId,
    val createdBy: GenericId,
    val content: RawMessageContent,
    val createdAt: Timestamp,
    val editedAt: Timestamp?,
    val deletedAt: Timestamp?,
    val reactions: List<RawReaction>,
    val isPinned: Boolean,
    val pinnedBy: GenericId?,
    val webhookId: GenericId?,
    val botId: GenericId?,
    val type: String
)

// Received when sending messages
@Serializable
data class RawPartialMessage(
    val id: UniqueId,
    val createdAt: Timestamp,
    val content: RawMessageContent,
    val type: String,
    val createdBy: GenericId,
    val isPrivate: Boolean,
    val repliesToIds: List<UniqueId>
)

@Serializable
data class RawMessageContent(
    @SerialName("object") val contentObject: String,
    val document: RawMessageContentNode
)

@Serializable
data class RawMessageContentNode(
    @SerialName("object") val documentObject: String,
    val data: RawMessageContentData = RawMessageContentData(),
    val type: OptionalProperty<String> = OptionalProperty.NotPresent,
    val nodes: List<RawMessageContentNode> = emptyList(),
    val leaves: OptionalProperty<List<RawMessageContentNodeLeaves>> = OptionalProperty.NotPresent
)

@Serializable
data class RawMessageContentData(
    val src: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: OptionalProperty<RawReaction> = OptionalProperty.NotPresent
)

@Serializable
data class RawMessageContentNodeLeaves(
    @SerialName("object") val leavesObject: String = "leaf",
    val text: String,
    @GuildedUnknown val marks: List<Unit> = emptyList()
)

@Serializable
data class RawSlowmode(
    val channelCooldown: Int,
    val cooldownRemaining: Int
)
