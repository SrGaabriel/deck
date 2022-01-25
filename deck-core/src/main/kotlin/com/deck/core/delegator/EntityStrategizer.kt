package com.deck.core.delegator

import com.deck.common.entity.RawChannel
import com.deck.common.entity.RawPartialSentMessage
import com.deck.common.entity.RawUser
import com.deck.common.util.GenericId
import com.deck.core.entity.*
import com.deck.gateway.entity.RawPartialTeamChannel
import com.deck.rest.entity.RawFetchedTeam
import com.deck.rest.request.SelfUserResponse
import java.util.*

public interface EntityStrategizer {
    public fun decodeTeam(raw: RawFetchedTeam): Team

    public fun decodeUser(raw: RawUser): User

    public fun decodeSelf(raw: SelfUserResponse): SelfUser

    public fun decodeChannel(raw: RawChannel): Channel

    public fun decodePartialSentMessage(channelId: UUID, teamId: GenericId?, raw: RawPartialSentMessage): Message

    public fun decodePartialTeamChannel(teamId: GenericId, raw: RawPartialTeamChannel): PartialTeamChannel
}