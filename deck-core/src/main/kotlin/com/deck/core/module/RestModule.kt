package com.deck.core.module

import com.deck.rest.RestClient
import com.deck.rest.route.*

public interface RestModule {
    public val restClient: RestClient

    public val authRoute: AuthRoute
    public val userRoute: UserRoute
    public val teamRoute: TeamRoute
    public val mediaRoute: MediaRoute
    public val channelRoute: ChannelRoute
}

public class DefaultRestModule : RestModule {
    override var restClient: RestClient = RestClient()
    override var authRoute: AuthRoute = AuthRoute(restClient)
    override var userRoute: UserRoute = UserRoute(restClient)
    override val teamRoute: TeamRoute = TeamRoute(restClient)
    override val mediaRoute: MediaRoute = MediaRoute(restClient)
    override var channelRoute: ChannelRoute = ChannelRoute(restClient)
}