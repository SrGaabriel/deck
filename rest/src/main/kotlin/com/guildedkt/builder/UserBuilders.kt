package com.guildedkt.builder

import com.guildedkt.entity.RawUserAboutInfo
import com.guildedkt.request.CreateDMChannelRequest
import com.guildedkt.request.ModifySelfUserRequest
import com.guildedkt.request.SetUserTransientStatusRequest
import com.guildedkt.util.Constants
import com.guildedkt.util.GameStatus
import com.guildedkt.util.GenericId
import com.guildedkt.util.PresenceType
import kotlin.random.Random

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