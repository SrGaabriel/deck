package com.guildedkt

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi

class RestClient {
    private var _token: String? = null
    var token: String
        get() = _token ?: error("Tried to access token when user is not logged yet.")
        set(value) { _token = value }

    @OptIn(ExperimentalSerializationApi::class)
    var httpClient: HttpClient = HttpClient(CIO.create()) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                explicitNulls = true
                encodeDefaults = false
                ignoreUnknownKeys = true
            })
            acceptContentTypes = acceptContentTypes + ContentType("application", "json")
        }
    }
}
