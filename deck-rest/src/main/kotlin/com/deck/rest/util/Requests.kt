package com.deck.rest.util

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.*
import io.ktor.http.*

public data class Request<S, G>(
    val method: HttpMethod,
    val url: String,
    val body: S? = null,
    val token: String? = null
)

public class RequestService(public val client: HttpClient, public val rateLimiter: RateLimiter = RateLimiter(client)) {
    public suspend inline fun <reified G> requestScope(
        failureHandler: FailureHandler = FailureHandler.Exceptional,
        crossinline block: suspend () -> HttpResponse,
    ): G? = block().let { response ->
        when {
            !response.status.isSuccess() -> failureHandler.onFailure(response)
            response is G -> response
            Unit is G -> Unit
            else -> response.receive()
        }
    }

    public suspend inline fun <reified G> constructRateLimitedRequest(
        failureHandler: FailureHandler = FailureHandler.Exceptional,
        crossinline block: suspend () -> HttpStatement
    ): G? = requestScope<G>(failureHandler) {
        rateLimiter.monitorRequest {
            block()
        }
    }

    public suspend inline fun <reified S, reified G> scheduleRequest(
        request: Request<S, G>,
        failureHandler: FailureHandler = FailureHandler.Exceptional,
    ): G? = constructRateLimitedRequest(failureHandler) {
        client.request(request.url) {
            body = request.body ?: EmptyContent
            method = request.method

            header(HttpHeaders.ContentType, ContentType.Application.Json)
            if (request.token != null)
                header(HttpHeaders.Authorization, "Bearer ${request.token}")
        }
    }
}

public suspend inline fun <reified S, reified G> RequestService.sendRequest(request: Request<S, G>): G =
    scheduleRequest(request, FailureHandler.Exceptional)!!

public suspend inline fun <reified S, reified G> RequestService.sendNullableRequest(request: Request<S, G>): G? =
    scheduleRequest(request, FailureHandler.Nullable)

public suspend inline fun <reified G> RequestService.exceptionalRequestScope(
    crossinline block: suspend () -> HttpResponse,
): G = requestScope(failureHandler = FailureHandler.Exceptional, block = block)!!

public suspend inline fun <reified G> RequestService.nullableRequestScope(
    crossinline block: suspend () -> HttpResponse,
): G? = requestScope(failureHandler = FailureHandler.Nullable, block = block)

public suspend inline fun <reified G> RequestService.constructExceptionalRateLimitedRequest(
    crossinline block: suspend () -> HttpStatement
): G = constructRateLimitedRequest<G>(failureHandler = FailureHandler.Exceptional, block = block)!!

public suspend inline fun <reified G> RequestService.constructNullableRateLimitedRequest(
    crossinline block: suspend () -> HttpStatement
): G? = constructRateLimitedRequest<G>(failureHandler = FailureHandler.Nullable, block = block)

public sealed class FailureHandler {
    public object Nullable: FailureHandler() {
        override suspend fun <G> onFailure(response: HttpResponse): G? {
            return null
        }
    }
    public object Exceptional: FailureHandler() {
        override suspend fun <G> onFailure(response: HttpResponse): G? {
            throw response.receive<RawGuildedRequestException>().toException()
        }
    }

    public abstract suspend fun <G> onFailure(response: HttpResponse): G?
}