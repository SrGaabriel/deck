package io.github.srgaabriel.deck.gateway.event.type

import io.github.srgaabriel.deck.common.entity.RawWebhook
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.gateway.entity.RawServerRoleUpdate
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ServerRolesUpdated")
public data class GatewayServerRolesUpdatedEvent(
    val serverId: GenericId,
    val memberRoleIds: List<RawServerRoleUpdate>
): GatewayEvent()

@Serializable
@SerialName("ServerWebhookCreated")
public data class GatewayServerWebhookCreatedEvent(
    val serverId: GenericId,
    val webhook: RawWebhook
): GatewayEvent()

@Serializable
@SerialName("ServerWebhookUpdated")
public data class GatewayServerWebhookUpdatedEvent(
    val serverId: GenericId,
    val webhook: RawWebhook
): GatewayEvent()