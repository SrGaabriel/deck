package io.github.deck.rest.util

import io.github.deck.common.util.Constants
import io.github.deck.common.util.DeckInternalApi
import io.github.deck.rest.RestClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

public data class Request<S, G>(
    val method: HttpMethod,
    val api: String = Constants.GuildedRestApi,
    val endpoint: String,
    val body: S? = null,
    val token: String? = null
)

public class RequestService(
    public val client: HttpClient,
    public val rest: RestClient
) {
    @OptIn(DeckInternalApi::class)
    public suspend inline fun <reified S, reified G> superviseRequest(
        request: Request<S, G>,
        failureHandler: FailureHandler = FailureHandler
    ): G {
        val response = scheduleRequest(request)
        return if (!response.status.isSuccess()) {
            failureHandler.onFailure(response)
        } else {
            response.body()
        }
    }

    @DeckInternalApi
    public suspend inline fun <reified S, reified G> scheduleRequest(request: Request<S, G>): HttpResponse = client.request(request.api + request.endpoint) {
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