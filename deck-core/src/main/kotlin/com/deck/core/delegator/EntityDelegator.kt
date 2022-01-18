package com.deck.core.delegator

import com.deck.common.util.GenericId
import com.deck.core.entity.Channel
import com.deck.core.entity.SelfUser
import com.deck.core.entity.TeamChannel
import com.deck.core.entity.User
import com.deck.core.module.RestModule
import com.deck.rest.util.Route
import kotlinx.coroutines.CoroutineScope
import java.util.*

interface EntityDelegator: CoroutineScope {
    val rest: RestModule
    val strategizer: EntityStrategizer

    suspend fun getUser(id: GenericId): User?

    suspend fun getSelfUser(): SelfUser

    suspend fun getChannel(id: UUID, teamId: GenericId?): Channel?

    suspend fun getTeamChannel(id: UUID, teamId: GenericId): TeamChannel?

    suspend fun getPrivateChannel(id: UUID): Channel?

    suspend fun <T, R : Route> R.nullableRequest(block: suspend R.() -> T): T? = runCatching {
        block(this)
    }.onFailure { it.printStackTrace() }.getOrNull()
}
