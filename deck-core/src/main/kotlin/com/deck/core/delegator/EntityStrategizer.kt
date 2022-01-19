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

public interface EntityStrategizer {
    public fun decodeUser(raw: RawUser): User

    public fun decodeSelf(raw: RawUser): SelfUser

    public fun decodeChannel(raw: RawChannel): Channel

    public fun decodePartialSentMessage(channelId: UUID, teamId: GenericId?, raw: RawPartialSentMessage): Message
}
