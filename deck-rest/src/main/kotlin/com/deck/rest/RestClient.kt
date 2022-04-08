package com.deck.rest

import com.deck.rest.route.*
import com.deck.rest.util.DEFAULT_HTTP_CLIENT
import com.deck.rest.util.RequestService
import io.ktor.client.*

public class RestClient(public val token: String) {
    public var httpClient: HttpClient = DEFAULT_HTTP_CLIENT
    public val requestService: RequestService = RequestService(httpClient)

    public val channel: ChannelRoute = ChannelRoute(this)
    public val group: GroupRoute = GroupRoute(this)
    public val member: MemberRoute = MemberRoute(this)
    public val role: RoleRoute = RoleRoute(this)
    public val server: ServerRoute = ServerRoute(this)
    public val webhook: WebhookRoute = WebhookRoute(this)
}