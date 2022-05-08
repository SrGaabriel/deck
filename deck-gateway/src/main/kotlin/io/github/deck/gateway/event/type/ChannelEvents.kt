package io.github.deck.gateway.event.type

import io.github.deck.common.entity.RawListItem
import io.github.deck.common.util.GenericId
import io.github.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ListItemCompleted")
public data class GatewayListItemCompletedEvent(
    val serverId: GenericId,
    val listItem: RawListItem
): GatewayEvent()

@Serializable
@SerialName("ListItemCreated")
public data class GatewayListItemCreatedEvent(
    val serverId: GenericId,
    val listItem: RawListItem
): GatewayEvent()

@Serializable
@SerialName("ListItemUpdated")
public data class GatewayListItemUpdatedEvent(
    val serverId: GenericId,
    val listItem: RawListItem
): GatewayEvent()

@Serializable
@SerialName("ListItemDeleted")
public data class GatewayListItemDeletedEvent(
    val serverId: GenericId,
    val listItem: RawListItem
): GatewayEvent()

@Serializable
@SerialName("ListItemUncompleted")
public data class GatewayListItemUncompletedEvent(
    val serverId: GenericId,
    val listItem: RawListItem
): GatewayEvent()