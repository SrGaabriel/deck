package com.deck.core.util

import com.deck.common.entity.RawUserAboutInfo
import com.deck.common.util.nullableOptional
import com.deck.core.builder.DeckModifySelfBuilder
import com.deck.core.entity.SelfUser
import com.deck.rest.route.UserRoute

suspend fun UserRoute.editSelf(self: SelfUser, builder: DeckModifySelfBuilder.() -> Unit) = this.editSelf(self.id) {
    DeckModifySelfBuilder().apply(builder).let { builder ->
        name = builder.name
        avatar = builder.avatar
        subdomain = builder.subdomain
        aboutInfo = RawUserAboutInfo(builder.aboutInfo?.biography, builder.aboutInfo?.tagline.nullableOptional())
    }
}
