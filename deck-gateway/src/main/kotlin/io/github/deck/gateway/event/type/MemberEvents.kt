package io.github.deck.gateway.event.type

import io.github.deck.common.entity.RawServer
import io.github.deck.common.entity.RawServerBan
import io.github.deck.common.entity.RawServerMember
import io.github.deck.common.util.GenericId
import io.github.deck.gateway.entity.RawServerMemberInfo
import io.github.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("BotServerMembershipCreated")
public data class GatewayBotServerMembershipCreatedEvent(
    val server: RawServer,
    val createdBy: GenericId
): GatewayEvent()

@Serializable
@SerialName("BotServerMembershipDeleted")
public data class GatewayBotServerMembershipDeletedEvent(
    val server: RawServer,
    val deletedBy: GenericId
): GatewayEvent()

@Serializable
@SerialName("ServerMemberJoined")
public data class GatewayServerMemberJoinedEvent(
    val serverId: GenericId,
    val member: RawServerMember
): GatewayEvent()

@Serializable
@SerialName("ServerMemberUpdated")
public data class GatewayServerMemberUpdatedEvent(
    val serverId: GenericId,
    val userInfo: RawServerMemberInfo
): GatewayEvent()

@Serializable
@SerialName("ServerMemberRemoved")
public data class GatewayServerMemberRemovedEvent(
    val serverId: GenericId,
    val userId: GenericId,
    val isKick: Boolean,
    val isBan: Boolean
): GatewayEvent()

@Serializable
@SerialName("ServerMemberBanned")
public data class GatewayServerMemberBannedEvent(
    val serverId: GenericId,
    val serverMemberBan: RawServerBan
): GatewayEvent()

@Serializable
@SerialName("ServerMemberUnbanned")
public data class GatewayServerMemberUnbannedEvent(
    val serverId: GenericId,
    val serverMemberBan: RawServerBan
): GatewayEvent()