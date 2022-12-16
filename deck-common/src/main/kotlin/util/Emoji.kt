package io.github.srgaabriel.deck.common.util

import io.github.srgaabriel.deck.common.entity.RawEmote

public data class Emote(
    public val id: Int,
    public val name: String,
    public val url: String
) {
    val isAnimated: Boolean get() = url.endsWith(".gif")

    public companion object {
        public fun from(raw: RawEmote): Emote = Emote(
            id = raw.id,
            name = raw.name,
            url = raw.url
        )
    }
}