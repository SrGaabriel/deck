package com.deck.rest

import com.deck.common.util.GenericId
import com.deck.rest.util.DEFAULT_HTTP_CLIENT
import com.deck.rest.util.RequestService
import io.ktor.client.*

public class RestClient {
    public lateinit var token: String

    internal lateinit var _selfId: GenericId
    public val selfId: GenericId get() = _selfId

    public var httpClient: HttpClient = DEFAULT_HTTP_CLIENT
    public val requestService: RequestService = RequestService(httpClient)
}
