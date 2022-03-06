package com.deck.common.entity

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
public data class RawDocumentation(
    public val id: IntGenericId,
    public val serverId: GenericId,
    public val channelId: UniqueId,
    public val title: String,
    public val content: String,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    public val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent
)

@Serializable
public data class RawListItem(
    public val id: UniqueId,
    public val serverId: GenericId,
    public val channelId: UniqueId,
    public val message: String,
    public val note: OptionalProperty<String> = OptionalProperty.NotPresent,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val createdByWebhookId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent
)

@Serializable
public data class RawForumThread(
    val id: IntGenericId,
    val serverId: GenericId,
    val channelId: UniqueId,
    val title: OptionalProperty<String> = OptionalProperty.NotPresent,
    val content: OptionalProperty<String> = OptionalProperty.NotPresent,
    val createdAt: Instant,
    val createdBy: GenericId,
    val createdByWebhookId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent
)