package com.deck.rest.util

import kotlinx.serialization.Serializable

public class GuildedRequestException(
    code: String,
    message: String
) : RuntimeException("[$code] $message")

@Serializable
public data class RawGuildedRequestException(
    val code: String,
    val message: String
)

public fun RawGuildedRequestException.toException(): GuildedRequestException =
    GuildedRequestException(code, message)