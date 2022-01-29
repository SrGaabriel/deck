package com.deck.core.module

import com.deck.rest.RestClient
import com.deck.rest.route.ChannelRoute

public interface RestModule {
    public val restClient: RestClient

    public val channelRoute: ChannelRoute
}

public class DefaultRestModule(token: String): RestModule {
    override val restClient: RestClient = RestClient(token)

    override val channelRoute: ChannelRoute = ChannelRoute(restClient)
}