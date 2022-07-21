package io.github.deck.rest

import io.github.deck.common.log.DeckLogger
import io.github.deck.common.util.MicroutilsLogger
import io.github.deck.rest.route.ChannelRoute
import io.github.deck.rest.route.GroupRoute
import io.github.deck.rest.route.ServerRoute
import io.github.deck.rest.route.WebhookRoute
import io.github.deck.rest.util.RequestService
import io.github.deck.rest.util.createHttpClient
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*

public class RestClient(public val token: String) {
    public var logger: DeckLogger = MicroutilsLogger("Rest Client Logger")

    public var httpClient: HttpClient = createHttpClient()
    public val requestService: RequestService = RequestService(httpClient, this)

    // ALL by default
    public var logLevel: LogLevel
        get() = httpClient.plugin(Logging).level
        set(value) { httpClient.plugin(Logging).level = value }

    public var logRequests: Boolean = false

    public val channel: ChannelRoute = ChannelRoute(this)
    public val group: GroupRoute = GroupRoute(this)
    public val server: ServerRoute = ServerRoute(this)
    public val webhook: WebhookRoute = WebhookRoute(this)
}