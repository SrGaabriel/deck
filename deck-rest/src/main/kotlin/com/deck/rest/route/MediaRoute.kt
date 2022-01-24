package com.deck.rest.route

import com.deck.common.util.DynamicMediaType
import com.deck.common.util.GuildedMedia
import com.deck.rest.RestClient
import com.deck.rest.request.UploadMediaResponse
import com.deck.rest.util.Route
import com.deck.rest.util.constructExceptionalRateLimitedRequest
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import mu.KotlinLogging

public class MediaRoute(client: RestClient): Route(client) {
    @OptIn(InternalAPI::class)
    public suspend fun uploadMedia(bytes: ByteArray, type: DynamicMediaType): GuildedMedia {
        val response = client.requestService.constructExceptionalRateLimitedRequest<UploadMediaResponse> {
            client.httpClient.submitFormWithBinaryData(
                url = "$GuildedMediaEndpoint/upload?dynamicMediaTypeId=${type.name}",
                formData = formData {
                    append("file", bytes, Headers.build {
                        append(HttpHeaders.ContentType, ContentType.Image.JPEG)
                        append(HttpHeaders.ContentDisposition, "filename=FB_IMG_1594674179744.jpg")
                    })
                }
            )
        }
        logger.info { "[DECK] Uploaded media to guilded's cdn, received URL: ${response.url}" }
        return GuildedMedia(response.url, type)
    }

    public companion object {
        private val logger = KotlinLogging.logger {  }
        public const val GuildedMediaEndpoint: String = "https://media.guilded.gg/media"
    }
}