package com.deck.rest

import com.deck.common.util.GenericId
import com.deck.rest.util.DEFAULT_HTTP_CLIENT
import com.deck.rest.util.RequestService
import io.ktor.client.*
import kotlin.properties.Delegates

public class RestClient {
    public var token: String by Delegates.notNull()
    public var selfId: GenericId by Delegates.notNull()

    public var httpClient: HttpClient = DEFAULT_HTTP_CLIENT
    public val requestService: RequestService = RequestService(httpClient)
}
