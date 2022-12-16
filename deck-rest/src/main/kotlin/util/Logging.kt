package io.github.srgaabriel.deck.rest.util

import io.github.srgaabriel.deck.common.log.debug
import io.github.srgaabriel.deck.rest.RestClient
import io.ktor.client.plugins.logging.*

public class KtorDeckLoggerWrapper(public val client: RestClient): Logger {
    override fun log(message: String) {
        if (!client.logRequests)
            return
        val messageToBeLogged = message
            .replace(client.token, "<masked>") // Hide token
            .replace(client.token.drop(5), "<masked>") // Hide token in case it is logged without the default prefix (`gapi_`)
        client.logger.debug { messageToBeLogged }
    }
}