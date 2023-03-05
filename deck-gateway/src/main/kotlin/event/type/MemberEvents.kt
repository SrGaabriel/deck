package io.github.srgaabriel.deck.gateway.event.type

import io.github.srgaabriel.deck.common.entity.RawServer
import io.github.srgaabriel.deck.common.entity.RawServerBan
import io.github.srgaabriel.deck.common.entity.RawServerMember
import io.github.srgaabriel.deck.common.entity.RawUserSocialLink
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.gateway.entity.RawServerMemberInfo
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
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

@Serializable
@SerialName("ServerMemberSocialLinkCreated")
public data class GatewayServerMemberSocialLinkCreatedEvent(
    val serverId: GenericId,
    val socialLink: RawUserSocialLink
): GatewayEvent()

@Serializable
@SerialName("ServerMemberSocialLinkUpdated")
public data class GatewayServerMemberSocialLinkUpdatedEvent(
    val serverId: GenericId,
    val socialLink: RawUserSocialLink
): GatewayEvent()

@Serializable
@SerialName("ServerMemberSocialLinkDeleted")
public data class GatewayServerMemberSocialLinkDeletedEvent(
    val serverId: GenericId,
    val socialLink: RawUserSocialLink
): GatewayEvent()