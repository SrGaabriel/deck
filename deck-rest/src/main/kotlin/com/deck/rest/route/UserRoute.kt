package com.deck.rest.route

import com.deck.common.entity.*
import com.deck.common.util.GenericId
import com.deck.common.util.RawUserPresenceType
import com.deck.rest.RestClient
import com.deck.rest.builder.ModifySelfUserBuilder
import com.deck.rest.request.*
import com.deck.rest.util.Route
import io.ktor.http.*

public class UserRoute(client: RestClient) : Route(client) {
    public suspend fun getSelf(): RawSelfUser = sendRequest<RawSelfUser, Unit>(
        endpoint = "/me",
        method = HttpMethod.Get
    )

    public suspend fun getUser(id: GenericId): RawUser? = sendNullableRequest<UserResponse, Unit>(
        endpoint = "/users/$id",
        method = HttpMethod.Get
    )?.user

    public suspend fun editSelf(builder: ModifySelfUserBuilder.() -> Unit): Unit = sendRequest(
        endpoint = "/users/${client.selfId}/profilev2",
        method = HttpMethod.Put,
        body = ModifySelfUserBuilder().apply(builder).toRequest()
    )

    public suspend fun getUserDMs(): List<RawPrivateChannel> = sendRequest<UserDMResponse, Unit>(
        endpoint = "/users/${client.selfId}/channels",
        method = HttpMethod.Get
    ).channels

    public suspend fun updateSelfAvatar(url: String): SelfUpdateAvatarResponse = sendRequest(
        endpoint = "/users/me/profile/images",
        method = HttpMethod.Post,
        body = SelfUpdateAvatarRequest(imageUrl = url)
    )

    public suspend fun updateSelfBanner(url: String): SelfUpdateBannerResponse = sendRequest(
        endpoint = "/users/me/profile/images/banner",
        method = HttpMethod.Post,
        body = SelfUpdateAvatarRequest(imageUrl = url)
    )

    public suspend fun createDMChannel(users: List<GenericId>): List<RawPrivateChannel> = sendRequest<UserDMResponse, CreateDMChannelRequest>(
        endpoint = "/users/${client.selfId}/channels",
        method = HttpMethod.Post,
        body = CreateDMChannelRequest(users)
    ).channels

    public suspend fun getUserPosts(id: GenericId): List<RawUserPost> = sendRequest<List<RawUserPost>, Unit>(
        endpoint = "/users/$id/posts",
        method = HttpMethod.Get,
    )

    public suspend fun setSelfPresence(status: RawUserPresenceStatus): Unit = sendRequest(
        endpoint = "/users/me/presence",
        method = HttpMethod.Post,
        body = SetSelfPresenceRequest(status)
    )

    public suspend fun setSelfTransientStatus(game: Int): RawTransientStatus = sendRequest(
        endpoint = "/users/me/status/transient",
        method = HttpMethod.Post,
        body = SetUserTransientStatusRequest(1686, game, RawUserPresenceType.Game)
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
