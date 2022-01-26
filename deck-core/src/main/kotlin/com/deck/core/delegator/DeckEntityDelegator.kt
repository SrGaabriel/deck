package com.deck.core.delegator

import com.deck.common.util.GenericId
import com.deck.common.util.mapToModel
import com.deck.core.cache.CacheManager
import com.deck.core.entity.SelfUser
import com.deck.core.entity.Team
import com.deck.core.entity.User
import com.deck.core.entity.channel.Channel
import com.deck.core.entity.channel.TeamChannel
import com.deck.core.module.RestModule
import kotlinx.coroutines.Dispatchers
import java.util.*
import kotlin.coroutines.CoroutineContext

public class DeckEntityDelegator(
    override val rest: RestModule,
    override val decoder: EntityDecoder,
    override val cache: CacheManager
) : EntityDelegator {
    override val coroutineContext: CoroutineContext = Dispatchers.Default

    override suspend fun getTeam(id: GenericId): Team? {
        val team = rest.teamRoute.nullableRequest { getTeam(id) }?.team ?: return null
        return decoder.decodeTeam(team)
    }

    override suspend fun getUser(id: GenericId): User? {
        val response = rest.userRoute.nullableRequest { getUser(id) } ?: return null
        return decoder.decodeUser(response.user)
    }

    override suspend fun getSelfUser(): SelfUser =
        decoder.decodeSelf(rest.userRoute.getSelf())

    override suspend fun getChannel(id: UUID, teamId: GenericId?): Channel? {
        return when (teamId) {
            null -> getPrivateChannel(id)
            else -> getTeamChannel(id, teamId)
        }
    }

    override suspend fun getTeamChannel(id: UUID, teamId: GenericId): TeamChannel? {
        val channels = rest.teamRoute.nullableRequest { getTeamChannels(teamId) }?.channels ?: return null
        return decoder.decodeChannel(channels.first { it.id == id.mapToModel() }) as? TeamChannel
    }

    override suspend fun getPrivateChannel(id: UUID): Channel? {
        val channel = rest.channelRoute.nullableRequest { getChannel(id.mapToModel()) } ?: return null
        return decoder.decodeChannel(channel)
    }
}