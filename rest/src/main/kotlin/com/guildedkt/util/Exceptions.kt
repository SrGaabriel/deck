package com.guildedkt.util

import kotlinx.serialization.Serializable

class GuildedRequestException(
    code: String,
    message: String
): RuntimeException("{$code: $message}")

@Serializable
data class RawGuildedRequestException(
    val code: String,
    val message: String
)

fun RawGuildedRequestException.toException() =
    GuildedRequestException(code, message)