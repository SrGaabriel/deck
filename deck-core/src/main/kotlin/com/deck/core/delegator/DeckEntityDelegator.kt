package com.deck.core.delegator

import com.deck.common.util.GenericId
import com.deck.common.util.mapToModel
import com.deck.core.entity.*
import com.deck.core.module.RestModule
import kotlinx.coroutines.Dispatchers
import java.util.*
import kotlin.coroutines.CoroutineContext

public class DeckEntityDelegator(override val rest: RestModule, override val strategizer: EntityStrategizer) :
    EntityDelegator {
    override val coroutineContext: CoroutineContext = Dispatchers.Default

    override suspend fun getTeam(id: GenericId): Team? {
        val team = rest.teamRoute.nullableRequest { getTeam(id) }?.team ?: return null
        return strategizer.decodeTeam(team)
    }

    override suspend fun getUser(id: GenericId): User? {
        val response = rest.userRoute.nullableRequest { getUser(id) } ?: return null
        return strategizer.decodeUser(response.user)
    }

    override suspend fun getSelfUser(): SelfUser =
        strategizer.decodeSelf(rest.userRoute.getSelf().user)

    override suspend fun getChannel(id: UUID, teamId: GenericId?): Channel? = when (teamId) {
        null -> getPrivateChannel(id)
        else -> getTeamChannel(id, teamId)
    }

    override suspend fun getTeamChannel(id: UUID, teamId: GenericId): TeamChannel? {
        val channels = rest.teamRoute.nullableRequest { getTeamChannels(teamId) }?.channels ?: return null
        return strategizer.decodeChannel(channels.first { it.id == id.mapToModel() }) as? TeamChannel
    }

    override suspend fun getPrivateChannel(id: UUID): Channel? {
        val channel = rest.channelRoute.nullableRequest { getChannel(id.mapToModel()) } ?: return null
        return strategizer.decodeChannel(channel)
    }
}
