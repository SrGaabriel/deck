package com.deck.core.module

import com.deck.rest.RestClient
import com.deck.rest.route.*
import io.ktor.http.*
import kotlin.system.measureTimeMillis

public interface RestModule {
    public val restClient: RestClient

    public val authRoute: AuthRoute
    public val userRoute: UserRoute
    public val teamRoute: TeamRoute
    public val mediaRoute: MediaRoute
    public val groupRoute: GroupRoute
    public val channelRoute: ChannelRoute
}

public class DefaultRestModule : RestModule {
    override val restClient: RestClient = RestClient()
    override val authRoute: AuthRoute = AuthRoute(restClient)
    override val userRoute: UserRoute = UserRoute(restClient)
    override val teamRoute: TeamRoute = TeamRoute(restClient)
    override val mediaRoute: MediaRoute = MediaRoute(restClient)
    override val groupRoute: GroupRoute = GroupRoute(restClient)
    override val channelRoute: ChannelRoute = ChannelRoute(restClient)

    public suspend fun getPing(): Long = measureTimeMillis {
        authRoute.sendRequest<Unit, Unit>(
            endpoint = "/users/me/ping",
            method = HttpMethod.Put
        )
    }
}