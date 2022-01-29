package com.deck.rest

import com.deck.rest.util.DEFAULT_HTTP_CLIENT
import com.deck.rest.util.RequestService
import io.ktor.client.*

public class RestClient(public val token: String) {
    public var httpClient: HttpClient = DEFAULT_HTTP_CLIENT
    public val requestService: RequestService = RequestService(httpClient)
}