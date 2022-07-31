package io.github.deck.rest

import io.github.deck.common.log.DeckLogger
import io.github.deck.common.log.MicroutilsLogger
import io.github.deck.rest.route.ChannelRoutes
import io.github.deck.rest.route.GroupRoutes
import io.github.deck.rest.route.ServerRoutes
import io.github.deck.rest.route.WebhookRoutes
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

    public val channel: ChannelRoutes = ChannelRoutes(this)
    public val group: GroupRoutes = GroupRoutes(this)
    public val server: ServerRoutes = ServerRoutes(this)
    public val webhook: WebhookRoutes = WebhookRoutes(this)
}