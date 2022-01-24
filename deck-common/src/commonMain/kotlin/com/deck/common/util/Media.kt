package com.deck.common.util

public data class GuildedMedia(
    public val url: String,
    public val type: DynamicMediaType
)

@Suppress("FunctionName")
/**
 * Represents a [GuildedMedia] with [DynamicMediaType.ContentMedia] type.
 *
 * **Warning:** This must be used with a guilded media URL, don't try to invoke this
 * function with a generic url.
 */
public fun ContentMedia(url: String): GuildedMedia =
    GuildedMedia(url, DynamicMediaType.ContentMedia)

public enum class DynamicMediaType {
    CustomReaction,
    ContentMedia,
    UserAvatar,
    UserBanner,
    TeamAvatar,
    TeamBanner,
    GroupAvatar
}