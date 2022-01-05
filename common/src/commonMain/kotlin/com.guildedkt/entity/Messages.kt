package com.guildedkt.entity

import com.guildedkt.util.GenericId
import com.guildedkt.util.GuildedUnknown
import com.guildedkt.util.Timestamp
import com.guildedkt.util.UniqueId
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

@Serializable
data class RawMessageContent(
    @SerialName("object") val contentObject: String,
)

@Serializable
data class RawMessageContentNode(
    @SerialName("object") val documentObject: String,
    val data: RawMessageContentData,
    val type: String?,
    val nodes: List<RawMessageContentNode>
)

@Serializable
data class RawMessageContentData(
    val reaction: RawReaction
)

@Serializable
data class RawMessageContentNodeLeaves(
    @SerialName("object") val leavesObject: String = "leaf",
    val text: String,
    @GuildedUnknown val marks: List<Unit> = emptyList()
)
