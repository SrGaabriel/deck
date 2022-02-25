package com.deck.core.builder

import com.deck.common.entity.RawUserAboutInfo
import com.deck.common.util.nullableOptional
import com.deck.core.entity.misc.DeckUserAboutInfo
import com.deck.rest.builder.RequestBuilder
import com.deck.rest.request.ModifySelfUserRequest

public class DeckModifySelfBuilder : RequestBuilder<ModifySelfUserRequest> {
    public var name: String? = null
    public var avatar: String? = null
    public var subdomain: String? = null
    public var aboutInfo: DeckUserAboutInfo? = null

    override fun toRequest(): ModifySelfUserRequest = ModifySelfUserRequest(
        name = name,
        avatar = avatar,
        subdomain = subdomain,
        aboutInfo = RawUserAboutInfo(aboutInfo?.biography.nullableOptional(), aboutInfo?.tagline.nullableOptional())
    )
}
