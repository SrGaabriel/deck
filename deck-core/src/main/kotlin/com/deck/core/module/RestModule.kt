package com.deck.core.module

import com.deck.rest.RestClient
import com.deck.rest.route.*

public interface RestModule {
    public val restClient: RestClient

    public val channelRoute: ChannelRoute
    public val groupRoute: GroupRoute
    public val memberRoute: MemberRoute
    public val roleRoute: RoleRoute
    public val serverRoute: ServerRoute
}

public class DefaultRestModule(token: String): RestModule {
    override val restClient: RestClient = RestClient(token)

    override val channelRoute: ChannelRoute = ChannelRoute(restClient)
    override val groupRoute: GroupRoute = GroupRoute(restClient)
    override val memberRoute: MemberRoute = MemberRoute(restClient)
    override val roleRoute: RoleRoute = RoleRoute(restClient)
    override val serverRoute: ServerRoute = ServerRoute(restClient)
}