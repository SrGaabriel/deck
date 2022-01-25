package com.deck.rest.builder

import com.deck.common.entity.RawUserAboutInfo
import com.deck.common.util.GenericId
import com.deck.rest.request.CreateDMChannelRequest
import com.deck.rest.request.ModifySelfUserRequest

public class ModifySelfUserBuilder : RequestBuilder<ModifySelfUserRequest> {
    public var name: String? = null
    public var avatar: String? = null
    public var subdomain: String? = null
    public var aboutInfo: RawUserAboutInfo? = null

    override fun toRequest(): ModifySelfUserRequest = ModifySelfUserRequest(
        name,
        avatar,
        subdomain,
        aboutInfo
    )
}

public class CreateDMChannelBuilder : RequestBuilder<CreateDMChannelRequest> {
    public var users: List<GenericId>? = null

    override fun toRequest(): CreateDMChannelRequest = CreateDMChannelRequest(
        users!!
    )
}