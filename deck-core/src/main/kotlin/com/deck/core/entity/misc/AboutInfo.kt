package com.deck.core.entity.misc

import com.deck.common.entity.RawUserAboutInfo
import com.deck.common.util.asNullable

/**
 * @param biography the user's biography, can be found in the 'My Profile' menu
 * @param tagline the user's tagline, can be found in the 'My Profile' menu
 * Both are empty when not defined.
 *
 * **Note:** Those are not to be confused with your status.
 */
public data class DeckUserAboutInfo(
    val biography: String,
    val tagline: String?
) {
    public companion object {
        public val Empty: DeckUserAboutInfo = DeckUserAboutInfo("", "")
    }
}

internal fun RawUserAboutInfo?.forcefullyWrap(): DeckUserAboutInfo {
    return DeckUserAboutInfo(
        biography = this?.bio.orEmpty(),
        tagline = this?.tagLine?.asNullable()
    )
}
