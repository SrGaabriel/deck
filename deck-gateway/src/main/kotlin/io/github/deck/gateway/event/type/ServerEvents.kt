package io.github.deck.gateway.event.type

import io.github.deck.common.entity.RawWebhook
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TeamXpAdded")
public data class GatewayServerXpAddedEvent(
    public val userIds: List<GenericId>,
    public val amount: Int,
    public val serverId: GenericId
): GatewayEvent()

@Serializable
@SerialName("teamRolesUpdated")
public data class GatewayServerRolesUpdatedEvent(
    val serverId: GenericId,
    val memberRoleIds: List<IntGenericId>
): GatewayEvent()

@Serializable
@SerialName("TeamWebhookCreated")
public data class GatewayServerWebhookCreatedEvent(
    val serverId: GenericId,
    val webhook: RawWebhook
): GatewayEvent()

@Serializable
@SerialName("TeamWebhookUpdated")
public data class GatewayServerWebhookUpdatedEvent(
    val serverId: GenericId,
    val webhook: RawWebhook
): GatewayEvent()