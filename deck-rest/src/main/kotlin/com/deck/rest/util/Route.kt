package com.deck.rest.util

import com.deck.common.util.Constants
import com.deck.rest.RestClient
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * This will fire a request and throw an [GuildedRequestException] in case of failure.
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
    authenticated: Boolean = true
): R = requestService.superviseRequest(
    request = Request(
        method,
        Constants.GuildedRestApi + endpoint,
        body,
        if (authenticated) token else null
    )
)

internal val DEFAULT_HTTP_CLIENT = HttpClient(CIO.create()) {
    install(ContentNegotiation) {
        json(Json {
            encodeDefaults = false
        })
    }
    install(HttpRequestRetry) {
        maxRetries = 5
        retryIf { request, response ->
            response.status == HttpStatusCode.TooManyRequests
        }
        delayMillis(respectRetryAfterHeader = true) {
            8_000
        }
    }
    expectSuccess = false
}