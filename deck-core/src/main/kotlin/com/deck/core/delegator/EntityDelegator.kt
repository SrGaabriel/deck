package com.deck.core.delegator

import com.deck.common.util.GenericId
import com.deck.core.cache.CacheManager
import com.deck.core.entity.*
import com.deck.core.module.RestModule
import com.deck.rest.util.Route
import kotlinx.coroutines.CoroutineScope
import java.util.*

public interface EntityDelegator : CoroutineScope {
    public val rest: RestModule
    public val decoder: EntityDecoder
    public val cache: CacheManager

    public suspend fun getTeam(id: GenericId): Team?

    public suspend fun getUser(id: GenericId): User?

    public suspend fun getSelfUser(): SelfUser

    public suspend fun getChannel(id: UUID, teamId: GenericId?): Channel?

    public suspend fun getTeamChannel(id: UUID, teamId: GenericId): TeamChannel?

    public suspend fun getPrivateChannel(id: UUID): Channel?

    public suspend fun <T, R : Route> R.nullableRequest(block: suspend R.() -> T): T? = runCatching {
        block(this)
    }.onFailure { it.printStackTrace() }.getOrNull()
}