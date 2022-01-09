package com.deck.rest.util

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*

// Needs work
class Ratelimiter(val client: HttpClient) {
    suspend fun scheduleRequest(url: String, scope: HttpRequestBuilder.() -> Unit): HttpResponse {
        val response = client.request<HttpStatement>(url, scope).execute()
        println(response.status)
        return if (response.status == HttpStatusCode.TooManyRequests) {
            // Provide some nice logs here
            delay((response.headers[HttpHeaders.RetryAfter]?.toIntOrNull() ?: 5) * 1000L)
            client.request<HttpStatement>(url, scope).execute()
        } else {
            response
        }
    }
}