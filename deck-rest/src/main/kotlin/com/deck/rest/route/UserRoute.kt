package com.deck.rest.route

import com.deck.common.entity.RawTransientStatus
import com.deck.common.entity.RawUserPost
import com.deck.common.entity.RawUserPresenceStatus
import com.deck.common.util.GenericId
import com.deck.rest.RestClient
import com.deck.rest.builder.CreateDMChannelBuilder
import com.deck.rest.builder.ModifySelfUserBuilder
import com.deck.rest.builder.SetUserTransientStatusBuilder
import com.deck.rest.request.*
import com.deck.rest.util.Route
import io.ktor.http.*

public class UserRoute(client: RestClient) : Route(client) {
    public suspend fun getSelf(): SelfUserResponse = sendRequest<SelfUserResponse, Unit>(
        endpoint = "/me",
        method = HttpMethod.Get
    )

    public suspend fun getUser(id: GenericId): UserResponse? = sendNullableRequest<UserResponse, Unit>(
        endpoint = "/users/$id",
        method = HttpMethod.Get
    )

    public suspend fun editSelf(
        selfId: GenericId,
        builder: ModifySelfUserBuilder.() -> Unit
    ): Unit = sendRequest<Unit, ModifySelfUserRequest>(
        endpoint = "/users/$selfId/profilev2",
        method = HttpMethod.Put,
        body = ModifySelfUserBuilder().apply(builder).toRequest()
    )

    public suspend fun leaveTeam(teamId: GenericId, selfId: GenericId): Unit = sendRequest<Unit, Unit>(
        endpoint = "/teams/$teamId/members/$selfId",
        method = HttpMethod.Delete
    )

    public suspend fun getUserDMs(selfId: GenericId): UserDMResponse = sendRequest<UserDMResponse, Unit>(
        endpoint = "/users/$selfId/channels",
        method = HttpMethod.Get
    )

    public suspend fun updateSelfAvatar(url: String): SelfUpdateAvatarResponse =
        sendRequest<SelfUpdateAvatarResponse, SelfUpdateAvatarRequest>(
            endpoint = "/users/me/profile/images",
            method = HttpMethod.Post,
            body = SelfUpdateAvatarRequest(imageUrl = url)
        )

    public suspend fun updateSelfBanner(url: String): SelfUpdateBannerResponse =
        sendRequest<SelfUpdateBannerResponse, SelfUpdateAvatarRequest>(
            endpoint = "/users/me/profile/images/banner",
            method = HttpMethod.Post,
            body = SelfUpdateAvatarRequest(imageUrl = url)
        )

    public suspend fun createDMChannel(selfId: GenericId, builder: CreateDMChannelBuilder.() -> Unit): UserDMResponse =
        sendRequest<UserDMResponse, CreateDMChannelRequest>(
            endpoint = "/users/$selfId/channels",
            method = HttpMethod.Post,
            body = CreateDMChannelBuilder().apply(builder).toRequest()
        )

    public suspend fun getUserPosts(id: GenericId): List<RawUserPost> = sendRequest<List<RawUserPost>, Unit>(
        endpoint = "/users/$id/posts",
        method = HttpMethod.Get,
    )

    public suspend fun setSelfPresence(status: RawUserPresenceStatus): Unit = sendRequest<Unit, SetSelfPresenceRequest>(
        endpoint = "/users/me/presence",
        method = HttpMethod.Post,
        body = SetSelfPresenceRequest(status)
    )

    public suspend fun setSelfTransientStatus(builder: SetUserTransientStatusBuilder.() -> Unit): RawTransientStatus =
        sendRequest<RawTransientStatus, SetUserTransientStatusRequest>(
            endpoint = "/users/me/status/transient",
            method = HttpMethod.Post,
            body = SetUserTransientStatusBuilder().apply(builder).toRequest()
        )

    public suspend fun deleteSelfTransientStatus(): Unit = sendRequest<Unit, Unit>(
        endpoint = "/users/me/status/transient",
        method = HttpMethod.Delete
    )

    public suspend fun getSelfReferralStatistics(): SelfReferralStatisticsResponse =
        sendRequest<SelfReferralStatisticsResponse, Unit>(
            endpoint = "/users/me/referrals",
            method = HttpMethod.Get
        )
}
