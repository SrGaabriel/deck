package com.deck.rest.route

import com.deck.common.entity.RawEmoji
import com.deck.common.entity.RawGroup
import com.deck.common.entity.RawWebhook
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.rest.RestClient
import com.deck.rest.builder.*
import com.deck.rest.entity.RawFetchedMember
import com.deck.rest.entity.RawFetchedTeam
import com.deck.rest.request.*
import com.deck.rest.util.Route
import io.ktor.http.*

public class TeamRoute(client: RestClient) : Route(client) {
    public suspend fun createTeam(builder: CreateTeamBuilder.() -> Unit): Unit =
        sendRequest(
            endpoint = "/teams",
            method = HttpMethod.Post,
            body = CreateTeamBuilder(client.selfId).apply(builder).toRequest()
        )

    public suspend fun getTeam(teamId: GenericId): RawFetchedTeam = sendRequest<GetTeamResponse, Unit>(
        endpoint = "/teams/$teamId",
        method = HttpMethod.Get
    ).team

    public suspend fun getTeamChannels(teamId: GenericId): GetTeamChannelsResponse =
        sendRequest<GetTeamChannelsResponse, Unit>(
            endpoint = "/teams/$teamId/channels",
            method = HttpMethod.Get
        )

    public suspend fun updateTeam(teamId: GenericId, builder: TeamUpdateBuilder.() -> Unit): Unit =
        sendRequest(
            endpoint = "/teams/$teamId/games/null/settings",
            method = HttpMethod.Put,
            body = TeamUpdateBuilder().apply(builder).toRequest()
        )

    public suspend fun disbandTeam(teamId: GenericId): Unit = sendRequest<Unit, Unit>(
        endpoint = "/teams/$teamId",
        method = HttpMethod.Delete
    )

    public suspend fun getMembers(teamId: GenericId): List<RawFetchedMember> = sendRequest<GetTeamMembersResponse, Unit>(
        endpoint = "/teams/$teamId/members",
        method = HttpMethod.Get
    ).members

    public suspend fun setNickname(
        teamId: GenericId,
        memberId: GenericId,
        nickname: String
    ): Unit = sendRequest(
        endpoint = "/teams/$teamId/members/$memberId/nickname",
        method = HttpMethod.Put,
        body = SetNicknameRequest(nickname)
    )

    public suspend fun resetNickname(teamId: GenericId, memberId: GenericId): Unit = sendRequest<Unit, Unit>(
        endpoint = "/teams/$teamId/members/$memberId/nickname",
        method = HttpMethod.Delete
    )

    public suspend fun setMemberXp(
        teamId: GenericId,
        memberId: GenericId,
        amount: Long
    ): Unit = sendRequest(
        endpoint = "/teams/$teamId/members/$memberId/xp",
        method = HttpMethod.Put,
        body = SetMemberXPRequest(amount)
    )

    public suspend fun kickMember(teamId: GenericId, memberId: GenericId): Unit = sendRequest<Unit, Unit>(
        endpoint = "/teams/$teamId/members/$memberId",
        method = HttpMethod.Delete
    )

    public suspend fun getBans(teamId: GenericId): String = sendRequest<String, Unit>(
        endpoint = "/teams/$teamId/members/ban",
        method = HttpMethod.Get
    )

    public suspend fun banMember(
        teamId: GenericId,
        memberId: GenericId,
        builder: BanMemberBuilder.() -> Unit = {}
    ): Unit =
        sendRequest(
            endpoint = "/teams/$teamId/members/$memberId/ban",
            method = HttpMethod.Delete,
            body = BanMemberBuilder().apply(builder).toRequest()
        )

    public suspend fun unbanMember(teamId: GenericId, memberId: GenericId): Unit = sendRequest<Unit, Unit>(
        endpoint = "/teams/$teamId/members/$memberId/ban",
        method = HttpMethod.Put
    )

    public suspend fun joinTeam(teamId: GenericId): String = sendRequest<String, Unit>(
        endpoint = "/teams/$teamId/members/${client.selfId}/join",
        method = HttpMethod.Put
    )

    public suspend fun getInvites(teamId: GenericId): String = sendRequest<String, Unit>(
        endpoint = "/teams/$teamId/hash_invites",
        method = HttpMethod.Get
    )

    public suspend fun createInvite(teamId: GenericId): PartialInviteResponse =
        sendRequest<CreateInviteResponse, CreateInviteRequest>(
            endpoint = "/teams/$teamId/invites",
            method = HttpMethod.Post,
            body = CreateInviteRequest(teamId)
        ).invite

    public suspend fun useInvite(inviteId: GenericId): String = sendRequest<String, Unit>(
        endpoint = "/invites/$inviteId",
        method = HttpMethod.Put
    )

    public suspend fun deleteRole(teamId: GenericId, roleId: IntGenericId): Unit = sendRequest<Unit, Unit>(
        endpoint = "/teams/$teamId/roles/$roleId",
        method = HttpMethod.Delete
    )

    public suspend fun createGroup(teamId: GenericId, builder: CreateGroupBuilder.() -> Unit): CreateGroupResponse =
        sendRequest(
            endpoint = "/teams/$teamId/groups",
            method = HttpMethod.Post,
            body = CreateGroupBuilder().apply(builder).toRequest()
        )

    public suspend fun updateGroup(
        teamId: GenericId,
        groupId: GenericId,
        builder: CreateGroupBuilder.() -> Unit
    ): RawGroup = sendRequest<UpdateGroupResponse, CreateGroupRequest>(
        endpoint = "/teams/$teamId/groups/$groupId",
        method = HttpMethod.Put,
        body = CreateGroupBuilder().apply(builder).toRequest()
    ).group

    public suspend fun deleteGroup(teamId: GenericId, groupId: GenericId): Unit = sendRequest<Unit, Unit>(
        endpoint = "/teams/$teamId/groups/$groupId",
        method = HttpMethod.Delete
    )

    public suspend fun createEmoji(teamId: GenericId, builder: CreateEmojiBuilder.() -> Unit): RawEmoji =
        sendRequest(
            endpoint = "/teams/$teamId/customReaction",
            method = HttpMethod.Put,
            body = CreateEmojiBuilder().apply(builder).toRequest()
        )

    public suspend fun deleteEmoji(teamId: GenericId, emojiId: IntGenericId): Unit = sendRequest<Unit, Unit>(
        endpoint = "/teams/$teamId/customReactions/$emojiId",
        method = HttpMethod.Delete
    )

    public suspend fun updateEmoji(
        teamId: GenericId,
        emojiId: IntGenericId,
        newName: String
    ): Unit = sendRequest(
        endpoint = "/teams/$teamId/customReactions/$emojiId",
        method = HttpMethod.Put,
        body = UpdateEmojiRequest(newName)
    )

    public suspend fun createWebhook(builder: CreateWebhookBuilder.() -> Unit): RawWebhook =
        sendRequest(
            endpoint = "/webhooks",
            method = HttpMethod.Post,
            body = CreateWebhookBuilder().apply(builder).toRequest()
        )

    public suspend fun editWebhook(webhookId: String, builder: CreateWebhookBuilder.() -> Unit): RawWebhook =
        sendRequest(
            endpoint = "/webhooks/$webhookId",
            method = HttpMethod.Put,
            body = CreateWebhookBuilder().apply(builder).toRequest()
        )

    public suspend fun deleteWebhook(webhookId: String): Unit = sendRequest<Unit, Unit>(
        endpoint = "/webhooks/$webhookId",
        method = HttpMethod.Delete
    )

    public suspend fun addRole(teamId: GenericId, roleId: IntGenericId, memberId: GenericId): Unit =
        sendRequest<Unit, Unit>(
            endpoint = "/teams/$teamId/roles/$roleId/users/$memberId",
            method = HttpMethod.Put
        )

    public suspend fun removeRole(teamId: GenericId, roleId: IntGenericId, memberId: GenericId): Unit =
        sendRequest<Unit, Unit>(
            endpoint = "/teams/$teamId/roles/$roleId/users/$memberId",
            method = HttpMethod.Delete
        )
}