package io.github.srgaabriel.deck.core.util

import io.github.srgaabriel.deck.common.entity.ServerChannelType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.stateless.*
import io.github.srgaabriel.deck.core.stateless.channel.*
import io.github.srgaabriel.deck.core.stateless.channel.content.*
import java.util.*

public fun StatelessMessage(
    client: DeckClient,
    id: UUID,
    channelId: UUID,
    serverId: GenericId?
): StatelessMessage = BlankStatelessMessage(client, id, channelId, serverId)

internal data class BlankStatelessMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val channelId: UUID,
    override val serverId: GenericId?
): StatelessMessage

public fun StatelessServerChannel(
    client: DeckClient,
    id: UUID,
    serverId: GenericId
): StatelessServerChannel = BlankStatelessServerChannel(client, id, serverId)

internal data class BlankStatelessServerChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId
): StatelessServerChannel {
    override val type: ServerChannelType
        get() = error("Tried to get a stateless generic server channel's type. Try fetching the channel with `.getChannel` and then accessing its type.")
}

public fun StatelessMessageChannel(
    client: DeckClient,
    id: UUID,
    serverId: GenericId?
): StatelessMessageChannel = BlankStatelessMessageChannel(client, id, serverId)

internal data class BlankStatelessMessageChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId?
): StatelessMessageChannel

public fun StatelessDocumentationChannel(
    client: DeckClient,
    id: UUID,
    serverId: GenericId
): StatelessDocumentationChannel = BlankStatelessDocumentationChannel(client, id, serverId)

internal data class BlankStatelessDocumentationChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId
): StatelessDocumentationChannel

public fun StatelessDocumentation(
    client: DeckClient,
    id: IntGenericId,
    channelId: UUID,
    serverId: GenericId
): StatelessDocumentation = BlankStatelessDocumentation(client, id, channelId, serverId)

internal data class BlankStatelessDocumentation(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val channelId: UUID,
    override val serverId: GenericId
): StatelessDocumentation

public fun StatelessDocumentationComment(
    client: DeckClient,
    id: IntGenericId,
    documentationId: IntGenericId,
    channelId: UUID,
    serverId: GenericId
): StatelessDocumentationComment = BlankStatelessDocumentationComment(client, id, documentationId, channelId, serverId)

internal data class BlankStatelessDocumentationComment(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val documentationId: IntGenericId,
    override val channelId: UUID,
    override val serverId: GenericId
): StatelessDocumentationComment

public fun StatelessListChannel(
    client: DeckClient,
    id: UUID,
    serverId: GenericId
): StatelessListChannel = BlankStatelessListChannel(client, id, serverId)

internal data class BlankStatelessListChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId
): StatelessListChannel

public fun StatelessForumChannel(
    client: DeckClient,
    id: UUID,
    serverId: GenericId
): StatelessForumChannel = BlankStatelessForumChannel(client, id, serverId)

internal class BlankStatelessForumChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId
): StatelessForumChannel

public fun StatelessForumTopic(
    client: DeckClient,
    id: IntGenericId,
    channelId: UUID,
    serverId: GenericId
): StatelessForumTopic = BlankStatelessForumTopic(client, id, channelId, serverId)

internal class BlankStatelessForumTopic(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val channelId: UUID,
    override val serverId: GenericId
): StatelessForumTopic

public fun StatelessForumTopicComment(
    client: DeckClient,
    id: IntGenericId,
    forumTopicId: IntGenericId,
    channelId: UUID,
    serverId: GenericId
): StatelessForumTopicComment = BlankStatelessForumTopicComment(client, id, forumTopicId, channelId, serverId)

internal class BlankStatelessForumTopicComment(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val forumTopicId: IntGenericId,
    override val channelId: UUID,
    override val serverId: GenericId
): StatelessForumTopicComment

public fun StatelessServer(
    client: DeckClient,
    id: GenericId,
): StatelessServer = BlankStatelessServer(client, id)

internal data class BlankStatelessServer(
    override val client: DeckClient,
    override val id: GenericId
): StatelessServer

public fun StatelessUser(
    client: DeckClient,
    id: GenericId,
): StatelessUser = BlankStatelessUser(client, id)

internal data class BlankStatelessUser(
    override val client: DeckClient,
    override val id: GenericId
): StatelessUser

public fun StatelessMember(
    client: DeckClient,
    id: GenericId,
    serverId: GenericId
): StatelessMember = BlankStatelessMember(client, id, serverId)

internal data class BlankStatelessMember(
    override val client: DeckClient,
    override val id: GenericId,
    override val serverId: GenericId
): StatelessMember

public fun StatelessListItem(
    client: DeckClient,
    id: UUID,
    serverId: GenericId,
    channelId: UUID
): StatelessListItem = BlankStatelessListItem(client, id, serverId, channelId)

internal data class BlankStatelessListItem(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId,
    override val channelId: UUID
): StatelessListItem

public fun StatelessCalendarChannel(
    client: DeckClient,
    id: UUID,
    serverId: GenericId
): StatelessCalendarChannel = BlankStatelessCalendarChannel(client, id, serverId)

internal data class BlankStatelessCalendarChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId
): StatelessCalendarChannel

public fun StatelessCalendarEvent(
    client: DeckClient,
    id: IntGenericId,
    channelId: UUID,
    serverId: GenericId,
): StatelessCalendarEvent = BlankStatelessCalendarEvent(client, id, channelId, serverId)

internal data class BlankStatelessCalendarEvent(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val channelId: UUID,
    override val serverId: GenericId,
): StatelessCalendarEvent

public fun StatelessCalendarEventSeries(
    client: DeckClient,
    id: UUID,
    channelId: UUID,
    serverId: GenericId,
): StatelessCalendarEventSeries = BlankStatelessCalendarEventSeries(client, id, channelId, serverId)

internal data class BlankStatelessCalendarEventSeries(
    override val client: DeckClient,
    override val id: UUID,
    override val channelId: UUID,
    override val serverId: GenericId,
): StatelessCalendarEventSeries

public fun StatelessCalendarEventRsvp(
    client: DeckClient,
    userId: GenericId,
    calendarEventId: IntGenericId,
    channelId: UUID,
    serverId: GenericId,
): StatelessCalendarEventRsvp = BlankStatelessCalendarEventRsvp(client, userId, calendarEventId, channelId, serverId)

internal data class BlankStatelessCalendarEventRsvp(
    override val client: DeckClient,
    override val userId: GenericId,
    override val calendarEventId: IntGenericId,
    override val channelId: UUID,
    override val serverId: GenericId
): StatelessCalendarEventRsvp

public fun StatelessCalendarEventComment(
    client: DeckClient,
    id: IntGenericId,
    calendarEventId: IntGenericId,
    channelId: UUID,
    serverId: GenericId
): StatelessCalendarEventComment = BlankStatelessCalendarEventComment(client, id, calendarEventId, channelId, serverId)

internal class BlankStatelessCalendarEventComment(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val calendarEventId: IntGenericId,
    override val channelId: UUID,
    override val serverId: GenericId
): StatelessCalendarEventComment

public fun StatelessWebhook(
    client: DeckClient,
    id: UUID,
    serverId: GenericId
): StatelessWebhook = BlankStatelessWebhook(client, id, serverId)

internal data class BlankStatelessWebhook(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId
): StatelessWebhook

public fun StatelessRole(
    client: DeckClient,
    id: IntGenericId,
    serverId: GenericId
): StatelessRole = BlankStatelessRole(client, id, serverId)

internal data class BlankStatelessRole(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val serverId: GenericId
): StatelessRole