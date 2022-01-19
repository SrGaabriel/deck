package com.deck.rest

import com.deck.rest.util.RequestService
import com.deck.rest.util.defaultHttpClient
import io.ktor.client.*

public class RestClient {
    private var _token: String? = null
    public var token: String
        get() = _token ?: error("Tried to access token when user is not logged yet.")
        set(value) {
            _token = value
        }

    public var httpClient: HttpClient = defaultHttpClient()
    public val requestService: RequestService = RequestService(httpClient)
}