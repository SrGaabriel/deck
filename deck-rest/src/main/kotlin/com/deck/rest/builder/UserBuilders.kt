package com.deck.rest.builder

import com.deck.common.entity.RawUserAboutInfo
import com.deck.common.util.Constants
import com.deck.common.util.GameStatus
import com.deck.common.util.GenericId
import com.deck.common.util.RawUserPresenceType
import com.deck.rest.request.CreateDMChannelRequest
import com.deck.rest.request.ModifySelfUserRequest
import com.deck.rest.request.SetUserTransientStatusRequest

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

public class SetUserTransientStatusBuilder : RequestBuilder<SetUserTransientStatusRequest> {
    public var id: Int = Constants.DefaultGenericIntId
    public var game: GameStatus? = null
    public var type: RawUserPresenceType? = null

    override fun toRequest(): SetUserTransientStatusRequest = SetUserTransientStatusRequest(
        id,
        game?.id,
        type!!
    )
}