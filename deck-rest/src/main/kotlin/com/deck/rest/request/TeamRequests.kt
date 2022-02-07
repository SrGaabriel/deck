package com.deck.rest.request

import com.deck.common.entity.*
import com.deck.common.util.GenericId
import com.deck.rest.entity.RawFetchedMember
import com.deck.rest.entity.RawFetchedTeam
import kotlinx.serialization.Serializable

@Serializable
public data class GetTeamResponse(
    val team: RawFetchedTeam
)

@Serializable
public data class GetTeamChannelsResponse(
    val channels: List<RawChannel>,
    val temporalChannels: List<RawChannel>,
    val categories: List<RawChannelCategory>
)

@Serializable
public data class CreateTeamRequest(
    val extraInfo: CreateTeamExtraInfoRequest,
    val teamName: String,
    val userId: GenericId,
    val avatar: String?
)

@Serializable
public data class CreateTeamExtraInfoRequest(
    val platform: String
)

@Serializable
public data class TeamUpdateRequest(
    val banner: String?,
    val teamName: String?,
    val description: String?,
    val subdomain: String?,
    val type: RawTeamType?,
    val timezone: String?,
    val isPublic: Boolean?,
    val notificationPreference: RawNotificationSettingsType?
)

@Serializable
public data class SetNicknameRequest(
    val nickname: String
)

@Serializable
public data class SetMemberXPRequest(
    val amount: Long
)

@Serializable
public data class BanMemberRequest(
    val deleteHistoryOption: Int?,
    val reason: String?
)

@Serializable
public data class CreateInviteRequest(
    val teamId: GenericId
)

@Serializable
public data class CreateInviteResponse(
    val invite: PartialInviteResponse
)

@Serializable
public data class PartialInviteResponse(
    val id: GenericId
)

@Serializable
public data class CreateGroupRequest(
    val name: String,
    val description: String?,
    val avatar: String?,
    val game: Int?,
    val membershipTeamRoleId: Int?,
    val additionalMembershipTeamRoleIds: List<Int> = emptyList(),
    val visibilityTeamRoleId: Int?,
    val isPublic: Boolean = false,
)

@Serializable
public data class CreateGroupResponse(
    val group: RawGroup,
    val channel: RawChannel,
    val voiceChannel: RawChannel
)

@Serializable
public data class UpdateGroupResponse(
    val group: RawGroup
)

@Serializable
public data class CreateEmojiRequest(
    val name: String,
    val apng: String,
    val png: String,
    val webp: String
)

@Serializable
public data class UpdateEmojiRequest(
    val name: String
)

@Serializable
public data class CreateWebhookRequest(
    val name: String,
    val channelId: GenericId
)

@Serializable
public data class GetTeamMembersResponse(
    val members: List<RawFetchedMember>
)