package io.github.srgaabriel.deck.gateway.event.type

import io.github.srgaabriel.deck.common.util.OptionalProperty
import io.github.srgaabriel.deck.gateway.entity.*
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ChannelMessageReactionCreated")
public data class GatewayChatMessageReactionCreatedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawMessageReaction
): GatewayEvent()

@Serializable
@SerialName("ChannelMessageReactionDeleted")
public data class GatewayChatMessageReactionDeletedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawMessageReaction
): GatewayEvent()

@Serializable
@SerialName("ForumTopicReactionCreated")
public data class GatewayForumTopicReactionCreatedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawForumTopicReaction
): GatewayEvent()

@Serializable
@SerialName("ForumTopicReactionDeleted")
public data class GatewayForumTopicReactionDeletedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawForumTopicReaction
): GatewayEvent()

@Serializable
@SerialName("ForumTopicCommentReactionCreated")
public data class GatewayForumTopicCommentReactionCreatedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawForumTopicCommentReaction
): GatewayEvent()

@Serializable
@SerialName("ForumTopicCommentReactionDeleted")
public data class GatewayForumTopicCommentReactionDeletedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawForumTopicCommentReaction
): GatewayEvent()

@Serializable
@SerialName("CalendarEventReactionCreated")
public data class GatewayCalendarEventReactionCreatedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawCalendarEventReaction
): GatewayEvent()

@Serializable
@SerialName("CalendarEventReactionDeleted")
public data class GatewayCalendarEventReactionDeletedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawCalendarEventReaction
): GatewayEvent()

@Serializable
@SerialName("CalendarEventCommentReactionCreated")
public data class GatewayCalendarEventCommentReactionCreatedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawCalendarEventCommentReaction
): GatewayEvent()

@Serializable
@SerialName("CalendarEventCommentReactionDeleted")
public data class GatewayCalendarEventCommentReactionDeletedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawCalendarEventCommentReaction
): GatewayEvent()

@Serializable
@SerialName("DocReactionCreated")
public data class GatewayDocumentationReactionCreatedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawDocumentationReaction
): GatewayEvent()

@Serializable
@SerialName("DocReactionDeleted")
public data class GatewayDocumentationReactionDeletedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawDocumentationReaction
): GatewayEvent()

@Serializable
@SerialName("DocCommentReactionCreated")
public data class GatewayDocumentationCommentReactionCreatedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawDocumentationCommentReaction
): GatewayEvent()

@Serializable
@SerialName("DocCommentReactionDeleted")
public data class GatewayDocumentationCommentReactionDeletedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawDocumentationCommentReaction
): GatewayEvent()