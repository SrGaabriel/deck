package com.deck.rest.util

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.*
import io.ktor.http.*

data class Request<S, G>(
    val method: HttpMethod,
    val url: String,
    val body: S? = null,
    val authentication: String? = null
)

class RequestService(val client: HttpClient, val ratelimiter: Ratelimiter = Ratelimiter(client)) {
    suspend inline fun <reified S, reified G> sendRequest(request: Request<S, G>): G =
        sendRequestWithDefaultSettings(request) { response ->
            throw response.receive<RawGuildedRequestException>().toException()
        }!!

    suspend inline fun <reified S, reified G> sendNullableRequest(request: Request<S, G>): G? =
        sendRequestWithDefaultSettings(request) {
            null
        }

    suspend inline fun <reified S, reified G> sendRequestWithDefaultSettings(request: Request<S, G>, onFailure: (HttpResponse) -> G?): G? = ratelimiter.scheduleRequest(request.url) {
        this.body = request.body ?: EmptyContent
        this.method = request.method

        header(HttpHeaders.ContentType, ContentType.Application.Json)
        if (request.authentication != null)
            header(HttpHeaders.Cookie, "hmac_signed_session=${request.authentication}")
    }.let { response ->
        // TODO: Workaround, change later
        println(response.receive<String>())
        return@let when {
            response is G -> response
            Unit is G -> Unit
            !response.status.isSuccess() -> onFailure(response)
            else -> response.receive()
        }
    }
}
