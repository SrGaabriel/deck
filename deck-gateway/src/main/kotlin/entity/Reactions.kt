@file:UseSerializers(UUIDSerializer::class)

package io.github.srgaabriel.deck.gateway.entity

import io.github.srgaabriel.deck.common.entity.RawEmote
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
public data class RawMessageReaction(
    val channelId: UUID,
    val messageId: UUID,
    val createdBy: GenericId,
    val emote: RawEmote
)

@Serializable
public data class RawForumTopicReaction(
    val channelId: UUID,
    val createdBy: GenericId,
    val emote: RawEmote,
    val forumTopicId: IntGenericId
)

@Serializable
public data class RawForumTopicCommentReaction(
    val channelId: UUID,
    val createdBy: GenericId,
    val emote: RawEmote,
    val forumTopicId: IntGenericId,
    val forumTopicCommentId: IntGenericId
)

@Serializable
public data class RawCalendarEventReaction(
    val channelId: UUID,
    val createdBy: GenericId,
    val emote: RawEmote,
    val calendarEventId: IntGenericId
)

@Serializable
public data class RawCalendarEventCommentReaction(
    val channelId: UUID,
    val createdBy: GenericId,
    val emote: RawEmote,
    val calendarEventId: IntGenericId,
    val calendarEventCommentId: IntGenericId
)

@Serializable
public data class RawDocumentationReaction(
    val channelId: UUID,
    val createdBy: GenericId,
    val emote: RawEmote,
    val docId: IntGenericId
)

@Serializable
public data class RawDocumentationCommentReaction(
    val channelId: UUID,
    val createdBy: GenericId,
    val emote: RawEmote,
    val docId: IntGenericId,
    val docCommentId: IntGenericId
)