package io.github.srgaabriel.deck.rest.util

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

// We inline the function, so it doesn't appear in stack traces.
@Suppress("NOTHING_TO_INLINE")
public inline fun RawGuildedRequestException.toException(): GuildedRequestException =
    GuildedRequestException(code, message)