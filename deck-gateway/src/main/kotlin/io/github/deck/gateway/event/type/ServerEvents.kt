package io.github.deck.gateway.event.type

import io.github.deck.common.entity.RawWebhook
import io.github.deck.common.util.GenericId
import io.github.deck.gateway.entity.RawServerRoleUpdate
import io.github.deck.gateway.event.GatewayEvent
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