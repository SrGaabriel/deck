package com.deck.core.entity.misc

import com.deck.common.entity.RawUserAboutInfo
import com.deck.common.util.asNullable

/**
 * @param biography the user's biography, can be found in the 'My Profile' menu
 * @param tagline the user's tagline, can be found in the 'My Profile' menu
 * Both are null when not defined.
 *
 * **Note:** Those are not to be confused with your status.
 */
public data class DeckUserAboutInfo(
    val biography: String?,
    val tagline: String?
) {
    public companion object {
        public val Empty: DeckUserAboutInfo = DeckUserAboutInfo(null, null)

        public fun from(raw: RawUserAboutInfo?): DeckUserAboutInfo = if (raw != null) DeckUserAboutInfo(
            biography = raw.bio,
            tagline = raw.tagLine.asNullable()
        ) else Empty
    }
}