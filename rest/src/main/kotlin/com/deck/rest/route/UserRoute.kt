package com.deck.rest.route

import com.deck.common.entity.RawTransientStatus
import com.deck.common.entity.RawUserPost
import com.deck.common.util.GenericId
import com.deck.rest.RestClient
import com.deck.rest.builder.CreateDMChannelBuilder
import com.deck.rest.builder.ModifySelfUserBuilder
import com.deck.rest.builder.SetUserTransientStatusBuilder
import com.deck.rest.request.*
import com.deck.rest.util.Route
import io.ktor.http.HttpMethod

class UserRoute(client: RestClient) : Route(client) {
    suspend fun getSelf() = sendRequest<SelfUserResponse, Unit>(
        endpoint = "/me",
        method = HttpMethod.Get
    )

    suspend fun getUser(id: GenericId) = sendRequest<UserResponse, Unit>(
        endpoint = "/users/$id",
        method = HttpMethod.Get
    )

    suspend fun editSelf(
        selfId: GenericId,
        builder: ModifySelfUserBuilder.() -> Unit
    ) = sendRequest<Unit, ModifySelfUserRequest>(
        endpoint = "/users/$selfId/profilev2",
        method = HttpMethod.Put,
        body = ModifySelfUserBuilder().apply(builder).toRequest()
    )

    suspend fun leaveTeam(teamId: GenericId, selfId: GenericId) = sendRequest<Unit, Unit>(
        endpoint = "/teams/$teamId/members/$selfId",
        method = HttpMethod.Delete
    )

    suspend fun getUserDMs(selfId: GenericId) = sendRequest<UserDMResponse, Unit>(
        endpoint = "/users/$selfId/channels",
        method = HttpMethod.Get
    )

    suspend fun createDMChannel(selfId: GenericId, builder: CreateDMChannelBuilder.() -> Unit) =
        sendRequest<UserDMResponse, CreateDMChannelRequest>(
            endpoint = "/users/$selfId/channels",
            method = HttpMethod.Post,
            body = CreateDMChannelBuilder().apply(builder).toRequest()
        )

    suspend fun getUserPosts(id: GenericId) = sendRequest<List<RawUserPost>, Unit>(
        endpoint = "/users/$id/posts",
        method = HttpMethod.Get,
    )

    suspend fun setSelfTransientStatus(builder: SetUserTransientStatusBuilder.() -> Unit) =
        sendRequest<RawTransientStatus, SetUserTransientStatusRequest>(
            endpoint = "/users/me/status/transient",
            method = HttpMethod.Post,
            body = SetUserTransientStatusBuilder().apply(builder).toRequest()
        )

    suspend fun deleteSelfTransientStatus() = sendRequest<Unit, Unit>(
        endpoint = "/users/me/status/transient",
        method = HttpMethod.Delete
    )

    suspend fun getSelfReferralStatistics() = sendRequest<SelfReferralStatisticsResponse, Unit>(
        endpoint = "/users/me/referrals",
        method = HttpMethod.Get
    )
}