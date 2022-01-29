package com.deck.rest.util

import io.ktor.client.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import mu.KotlinLogging

public class RateLimiter(public val client: HttpClient) {
    private val logger = KotlinLogging.logger {  }

    public suspend fun monitorRequest(executor: suspend () -> HttpStatement): HttpResponse {
        val response = executor().execute()
        return if (response.status == HttpStatusCode.TooManyRequests) {
            logger.info { "The client has been rate-limited, waiting to retry..." }
            delay((response.headers[HttpHeaders.RetryAfter]?.toIntOrNull() ?: 5) * 1000L)
            monitorRequest(executor)
        } else {
            response
        }
    }
}