package com.deck.core.delegator

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.cache.CacheManager
import com.deck.core.entity.Member
import com.deck.core.entity.SelfUser
import com.deck.core.entity.Team
import com.deck.core.entity.User
import com.deck.core.entity.channel.*
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

    public suspend fun getMember(id: GenericId, teamId: GenericId): Member?

    public suspend fun getTeamChannel(id: UUID, teamId: GenericId): TeamChannel?

    public suspend fun getTeamMembers(teamId: GenericId): Collection<Member>?

    public suspend fun getForumChannelThread(threadId: IntGenericId, channelId: UUID): ForumThread?

    public suspend fun getForumChannelThreads(channelId: UUID): List<ForumThread>?

    public suspend fun getForumChannelReply(replyId: IntGenericId, threadId: IntGenericId, teamId: GenericId, channelId: UUID): ForumPost?

    public suspend fun getForumChannelReplies(threadId: IntGenericId, teamId: GenericId, channelId: UUID): List<ForumPost>?

    public suspend fun getSchedulingChannelAvailability(id: IntGenericId, channelId: UUID): ScheduleAvailability?

    public suspend fun getSchedulingChannelAvailabilities(channelId: UUID): Collection<ScheduleAvailability>?

    public suspend fun getPrivateChannel(id: UUID): Channel?

    public suspend fun <T, R : Route> R.nullableRequest(block: suspend R.() -> T): T? = runCatching {
        block(this)
    }.onFailure { it.printStackTrace() }.getOrNull()
}