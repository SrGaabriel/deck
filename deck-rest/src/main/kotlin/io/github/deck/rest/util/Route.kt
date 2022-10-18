package io.github.deck.rest.util

import io.github.deck.common.util.Constants
import io.github.deck.rest.RestClient
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * This will fire a request to Guilded's API and throw an [GuildedRequestException] in case of failure.
 * This method sends a blank request expecting [R] as a response.
 *
 * @param R Response type (Special types: [Unit], [HttpResponse])
 *
 * @param endpoint The request endpoint (not the entire url)
 * @param method The request http method
 * @param authenticated Whether you need to provide an authentication for this request
 *
 * @throws GuildedRequestException in case of failure.
 * @return Returns an object of type [R] in case of success.
 */
public suspend inline fun <reified R> RestClient.sendRequest(
    endpoint: String,
    method: HttpMethod,
    authenticated: Boolean = true,
    address: GuildedAddress = GuildedAddress.API
): R = requestService.superviseRequest<Unit, R>(
    request = Request(
        method,
        Constants.GuildedRestApi,
        endpoint,
        null,
        if (authenticated) token else null,
        address
    )
)

/**
 * This will fire a request to Guilded's API and throw an [GuildedRequestException] in case of failure.
 *
 * @param R Response type (Special types: [Unit], [HttpResponse])
 * @param B Body type
 *
 * @param endpoint The request endpoint (not the entire url)
 * @param method The request http method
 * @param body Request body, null if empty
 * @param authenticated Whether you need to provide an authentication for this request
 *
 * @throws GuildedRequestException in case of failure.
 * @return Returns an object of type [R] in case of success.
 */
public suspend inline fun <reified R, reified B> RestClient.sendRequest(
    endpoint: String,
    method: HttpMethod,
    body: B? = null,
    authenticated: Boolean = true,
    address: GuildedAddress = GuildedAddress.API
): R = requestService.superviseRequest(
    request = Request(
        method,
        Constants.GuildedRestApi,
        endpoint,
        body,
        if (authenticated) token else null,
        address
    )
)

internal fun RestClient.createHttpClient() = HttpClient(CIO.create()) {
    install(ContentNegotiation) {
        json(Json {
            encodeDefaults = false
            ignoreUnknownKeys = true
        })
    }
    install(HttpRequestRetry) {
        maxRetries = 5
        retryIf { _, response ->
            response.status == HttpStatusCode.TooManyRequests
        }
        delayMillis(respectRetryAfterHeader = true) {
            response?.headers?.get(HttpHeaders.RetryAfter)?.toLongOrNull() ?: 10_000
        }
    }
    install(Logging) {
        level = LogLevel.ALL
        logger = KtorDeckLoggerWrapper(this@createHttpClient)
    }
    install(UserAgent) {
        agent = "deck-v0.7.1"
    }
    expectSuccess = false
}

