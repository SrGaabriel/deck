package com.deck.core.com.deck.core.builder

import com.deck.common.entity.RawUserAboutInfo
import com.deck.common.util.nullableOptional
import com.deck.core.com.deck.core.entity.misc.DeckUserAboutInfo
import com.deck.rest.builder.RequestBuilder
import com.deck.rest.request.ModifySelfUserRequest

class DeckModifySelfBuilder: RequestBuilder<ModifySelfUserRequest> {
    var name: String? = null
    var avatar: String? = null
    var subdomain: String? = null
    var aboutInfo: DeckUserAboutInfo? = null

    override fun toRequest(): ModifySelfUserRequest = ModifySelfUserRequest(
        name = name,
        avatar = avatar,
        subdomain = subdomain,
        aboutInfo = RawUserAboutInfo(aboutInfo?.biography, aboutInfo?.tagline.nullableOptional())
    )
}
