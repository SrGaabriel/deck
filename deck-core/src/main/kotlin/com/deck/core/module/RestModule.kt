package com.deck.core.module

import com.deck.rest.RestClient
import com.deck.rest.route.AuthRoute
import com.deck.rest.route.ChannelRoute
import com.deck.rest.route.UserRoute

interface RestModule {
    val restClient: RestClient

    val authRoute: AuthRoute
    val userRoute: UserRoute
    val channelRoute: ChannelRoute
}

class DefaultRestModule: RestModule {
    override var restClient: RestClient = RestClient()
    override var authRoute = AuthRoute(restClient)
    override var userRoute = UserRoute(restClient)
    override var channelRoute = ChannelRoute(restClient)
}
