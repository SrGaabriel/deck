package com.guildedkt.route

import com.guildedkt.RestClient
import com.guildedkt.builder.CreateDMChannelBuilder
import com.guildedkt.builder.ModifySelfUserBuilder
import com.guildedkt.builder.SetUserTransientStatusBuilder
import com.guildedkt.entity.RawTransientStatus
import com.guildedkt.entity.RawUserPost
import com.guildedkt.request.CreateDMChannelRequest
import com.guildedkt.request.ModifySelfUserRequest
import com.guildedkt.request.SelfReferralStatisticsResponse
import com.guildedkt.request.SelfUserResponse
import com.guildedkt.request.SetUserTransientStatusRequest
import com.guildedkt.request.UserDMResponse
import com.guildedkt.request.UserResponse
import com.guildedkt.util.GenericId
import com.guildedkt.util.Route
import com.guildedkt.util.sendRequest
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