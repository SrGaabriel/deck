package com.deck.rest.builder

import com.deck.common.entity.RawUserAboutInfo
import com.deck.common.util.Constants
import com.deck.common.util.GameStatus
import com.deck.common.util.GenericId
import com.deck.common.util.PresenceType
import com.deck.rest.request.CreateDMChannelRequest
import com.deck.rest.request.ModifySelfUserRequest
import com.deck.rest.request.SetUserTransientStatusRequest

class ModifySelfUserBuilder : RequestBuilder<ModifySelfUserRequest> {
    var name: String? = null
    var avatar: String? = null
    var subdomain: String? = null
    var aboutInfo: RawUserAboutInfo? = null

    override fun toRequest() = ModifySelfUserRequest(
        name,
        avatar,
        subdomain,
        aboutInfo
    )
}

class CreateDMChannelBuilder : RequestBuilder<CreateDMChannelRequest> {
    var users: List<GenericId>? = null

    override fun toRequest() = CreateDMChannelRequest(
        users!!
    )
}

class SetUserTransientStatusBuilder : RequestBuilder<SetUserTransientStatusRequest> {
    var id: Int = Constants.DefaultGenericIntId
    var game: GameStatus? = null
    var type: PresenceType? = null

    override fun toRequest() = SetUserTransientStatusRequest(
        id,
        game?.id,
        type!!.rawName
    )
}