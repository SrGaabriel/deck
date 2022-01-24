package com.deck.rest.request

import kotlinx.serialization.Serializable

@Serializable
public data class UploadMediaResponse(
    val url: String
)