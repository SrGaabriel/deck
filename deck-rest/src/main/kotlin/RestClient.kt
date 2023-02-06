package io.github.srgaabriel.deck.rest

import io.github.srgaabriel.deck.common.log.DeckLogger
import io.github.srgaabriel.deck.common.log.MicroutilsLogger
import io.github.srgaabriel.deck.rest.route.*
import io.github.srgaabriel.deck.rest.util.RequestService
import io.github.srgaabriel.deck.rest.util.createHttpClient
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
    public val user: UserRoutes = UserRoutes(this)
    public val webhook: WebhookRoutes = WebhookRoutes(this)
}