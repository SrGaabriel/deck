package com.deck.core.com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.core.com.deck.core.builder.DeckModifySelfBuilder
import com.deck.core.com.deck.core.entity.SelfUser
import com.deck.core.com.deck.core.entity.misc.DeckUserAboutInfo
import com.deck.core.com.deck.core.util.editSelf
import com.deck.rest.route.UserRoute

data class DeckSelfUser constructor(
    private val route: UserRoute,
    override val id: GenericId,
    override var name: String,
    override var subdomain: String?,
    override var avatar: String?,
    override var banner: String?,
    override var aboutInfo: DeckUserAboutInfo?,
    override val creationTime: Timestamp,
    override val lastLoginTime: Timestamp
): SelfUser {
    override suspend fun setName(name: String) = editProfile {
        this.name = name
        this@DeckSelfUser.name = name
    }

    override suspend fun setAvatar(url: String) {
        this.avatar = route.updateSelfAvatar(url).profilePicture
    }

    override suspend fun setBanner(url: String) {
        this.banner = route.updateSelfBanner(url).profileBannerSm
    }

    override suspend fun setSubdomain(subdomain: String) = editProfile {
        this.subdomain = subdomain
        this@DeckSelfUser.subdomain = subdomain
    }

    override suspend fun setAboutInfo(aboutInfo: DeckUserAboutInfo) = editProfile {
        this.aboutInfo = aboutInfo
        this@DeckSelfUser.aboutInfo = aboutInfo
    }

    override suspend fun editProfile(editor: DeckModifySelfBuilder.() -> Unit) =
        route.editSelf(this, editor)
}
