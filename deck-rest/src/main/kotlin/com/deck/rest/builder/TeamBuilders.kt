package com.deck.rest.builder

import com.deck.common.entity.RawNotificationSettingsType
import com.deck.common.entity.RawTeamType
import com.deck.common.util.GenericId
import com.deck.rest.request.*

public class CreateTeamBuilder(private val userId: GenericId): RequestBuilder<CreateTeamRequest> {
    public var name: String? = null
    public var avatar: String? = null

    override fun toRequest(): CreateTeamRequest = CreateTeamRequest(
        CreateTeamExtraInfoRequest("desktop"),
        name!!,
        userId,
        avatar
    )
}

public class TeamUpdateBuilder : RequestBuilder<TeamUpdateRequest> {
    public var banner: String? = null
    public var name: String? = null
    public var description: String? = null
    public var subdomain: String? = null
    public var type: RawTeamType? = null
    public var timezone: String? = null
    public var isPublic: Boolean? = null
    public var notificationPreference: RawNotificationSettingsType? = null

    override fun toRequest(): TeamUpdateRequest = TeamUpdateRequest(
        banner,
        name,
        description,
        subdomain,
        type,
        timezone,
        isPublic,
        notificationPreference
    )
}

public class BanMemberBuilder : RequestBuilder<BanMemberRequest> {
    public var deleteMessagesDays: Int? = null
    public var reason: String? = null

    override fun toRequest(): BanMemberRequest = BanMemberRequest(
        deleteMessagesDays,
        reason
    )
}

public class CreateGroupBuilder : RequestBuilder<CreateGroupRequest> {
    public var name: String? = null
    public var description: String? = null
    public var avatar: String? =null
    public var game: Int? = null
    public var membershipTeamRoleId: Int? = null
    public var additionalMembershipTeamRoleIds: List<Int> = emptyList()
    public var visibilityTeamRoleId: Int? = null
    public var isPublic: Boolean = false

    override fun toRequest(): CreateGroupRequest = CreateGroupRequest(
        name!!,
        description,
        avatar,
        game,
        membershipTeamRoleId,
        additionalMembershipTeamRoleIds,
        visibilityTeamRoleId,
        isPublic
    )
}

public class CreateEmojiBuilder : RequestBuilder<CreateEmojiRequest> {
    public var name: String? = null
    public var imageUrl: String? = null

    override fun toRequest(): CreateEmojiRequest = CreateEmojiRequest(
        name!!,
        imageUrl!!,
        imageUrl!!,
        imageUrl!!
    )
}

public class CreateWebhookBuilder : RequestBuilder<CreateWebhookRequest> {
    public var name: String? = null
    public var channelId: GenericId? = null

    override fun toRequest(): CreateWebhookRequest = CreateWebhookRequest(
        name!!,
        channelId!!
    )
}
