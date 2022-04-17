package io.github.deck.rest.util

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

public data class Request<S, G>(
    val method: HttpMethod,
    val url: String,
    val body: S? = null,
    val token: String? = null
)

public class RequestService(public val client: HttpClient) {
    public suspend inline fun <reified S, reified G> superviseRequest(
        request: Request<S, G>,
        failureHandler: FailureHandler = FailureHandler
    ): G {
        val response = scheduleRequest(request)
        return if (response.status.isSuccess().not()) {
            failureHandler.onFailure(response)
        } else {
            response.body()
        }
    }

    public suspend inline fun <reified S, reified G> scheduleRequest(request: Request<S, G>, ): HttpResponse = client.request(request.url) {
        if (request.body != null)
            setBody(request.body)
        method = request.method

        header(HttpHeaders.ContentType, ContentType.Application.Json)
        if (request.token != null)
            header(HttpHeaders.Authorization, "Bearer ${request.token}")
    }
}

public interface FailureHandler {
    public suspend fun <G> onFailure(response: HttpResponse): G

    public companion object Default: FailureHandler {
        override suspend fun <G> onFailure(response: HttpResponse): G {
            throw response.body<RawGuildedRequestException>().toException()
        }
    }
}