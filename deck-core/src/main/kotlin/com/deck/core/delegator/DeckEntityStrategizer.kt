package com.deck.core.delegator

import com.deck.common.content.node.NodeGlobalStrategy
import com.deck.common.entity.RawChannel
import com.deck.common.entity.RawChannelCategory
import com.deck.common.entity.RawPartialSentMessage
import com.deck.common.entity.RawUser
import com.deck.common.util.GenericId
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.*
import com.deck.core.entity.impl.*
import com.deck.gateway.entity.RawPartialTeamChannel
import java.util.*

public class DeckEntityStrategizer(private val client: DeckClient) : EntityStrategizer {
    override fun decodeUser(raw: RawUser): User = DeckUser(client, raw)

    override fun decodeSelf(raw: RawUser): SelfUser = DeckSelfUser(client, raw)

    override fun decodeChannel(raw: RawChannel): Channel = raw.run {
        when (teamId) {
            null -> DeckPrivateChannel(client, raw)
            else -> DeckTeamChannel(client, raw)
        }
    }

    override fun decodePartialSentMessage(channelId: UUID, teamId: GenericId?, raw: RawPartialSentMessage): Message =
        DeckMessage(
            client = client,
            id = raw.id.mapToBuiltin(),
            content = NodeGlobalStrategy.decodeContent(raw.content),
            channelId = channelId,
            createdAt = raw.createdAt,
            createdBy = raw.createdBy,
            updatedAt = null,
            updatedBy = null,
            isSilent = raw.isSilent,
            isPrivate = raw.isPrivate,
            teamId = teamId,
            repliesToId = raw.repliesToIds.firstOrNull()?.mapToBuiltin()
        )

    override fun decodeCategory(raw: RawChannelCategory): TeamChannelCategory =
        DeckTeamChannelCategory(client, raw)

    override fun decodeTeamPartialChannel(raw: RawPartialTeamChannel): PartialTeamChannel =
        DeckPartialTeamChannel(client, raw)
}
