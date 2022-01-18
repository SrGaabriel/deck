package com.deck.core.delegator

import com.deck.common.entity.RawChannel
import com.deck.common.entity.RawPartialSentMessage
import com.deck.common.entity.RawUser
import com.deck.common.util.GenericId
import com.deck.core.entity.Channel
import com.deck.core.entity.Message
import com.deck.core.entity.SelfUser
import com.deck.core.entity.User
import java.util.*

interface EntityStrategizer {
    fun decodeUser(raw: RawUser): User

    fun decodeSelf(raw: RawUser): SelfUser

    fun decodeChannel(raw: RawChannel): Channel

    fun decodePartialSentMessage(channelId: UUID, teamId: GenericId?, raw: RawPartialSentMessage): Message
}
