package io.github.deck.rest.util

import io.github.deck.common.log.debug
import io.github.deck.rest.RestClient
import io.ktor.client.plugins.logging.*

public class KtorDeckLoggerWrapper(public val client: RestClient): Logger {
    override fun log(message: String) {
        if (!client.logRequests)
            return
        client.logger.debug { " ${message.replace(client.token, "*".repeat(client.token.length))}" }
    }
}