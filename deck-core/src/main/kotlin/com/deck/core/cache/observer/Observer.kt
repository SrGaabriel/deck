package com.deck.core.cache.observer

import com.deck.core.cache.CacheManager
import com.deck.core.delegator.EntityDecoder
import com.deck.core.entity.impl.DeckMember
import com.deck.core.entity.impl.DeckUser
import com.deck.core.entity.misc.DeckUserAboutInfo
import com.deck.core.event.DeckEvent
import com.deck.core.event.channel.DeckTeamChannelCreateEvent
import com.deck.core.event.channel.DeckTeamChannelDeleteEvent
import com.deck.core.event.channel.DeckTeamChannelUpdateEvent
import com.deck.core.event.channel.DeckTeamChannelsDeleteEvent
import com.deck.core.event.message.DeckMessageCreateEvent
import com.deck.core.event.message.DeckMessageDeleteEvent
import com.deck.core.event.message.DeckMessageUpdateEvent
import com.deck.core.event.team.DeckMemberLeaveEvent
import com.deck.core.event.team.DeckMemberUpdateEvent
import com.deck.core.util.WrappedEventSupplier
import com.deck.core.util.nullableOr
import com.deck.core.util.or

public interface CacheUpdater {
    public val supplier: WrappedEventSupplier
    public val cache: CacheManager
    public val decoder: EntityDecoder

    public fun onMessageCreate(event: DeckMessageCreateEvent)
    public fun onMessageUpdate(event: DeckMessageUpdateEvent)
    public fun onMessageDelete(event: DeckMessageDeleteEvent)

    public fun onMemberUpdate(event: DeckMemberUpdateEvent)
    public fun onMemberLeave(event: DeckMemberLeaveEvent)

    public fun onTeamChannelCreate(event: DeckTeamChannelCreateEvent)
    public fun onTeamChannelUpdate(event: DeckTeamChannelUpdateEvent)
    public fun onTeamChannelDelete(event: DeckTeamChannelDeleteEvent)
    public fun onTeamChannelsDelete(event: DeckTeamChannelsDeleteEvent)

    public fun handleEvent(event: DeckEvent): Unit = when(event) {
        is DeckMessageCreateEvent -> onMessageCreate(event)
        is DeckMessageUpdateEvent -> onMessageUpdate(event)
        is DeckMessageDeleteEvent -> onMessageDelete(event)
        is DeckMemberUpdateEvent -> onMemberUpdate(event)
        is DeckMemberLeaveEvent -> onMemberLeave(event)
        is DeckTeamChannelCreateEvent -> onTeamChannelCreate(event)
        is DeckTeamChannelUpdateEvent -> onTeamChannelUpdate(event)
        is DeckTeamChannelDeleteEvent -> onTeamChannelDelete(event)
        is DeckTeamChannelsDeleteEvent -> onTeamChannelsDelete(event)
        else -> {}
    }
}

public open class DefaultCacheUpdater(
    override val supplier: WrappedEventSupplier,
    override val cache: CacheManager,
    override val decoder: EntityDecoder
): CacheUpdater {
    override fun onMessageCreate(event: DeckMessageCreateEvent) {
        cache.updateMessage(event.message.id, event.message)
    }

    override fun onMessageUpdate(event: DeckMessageUpdateEvent) {
        cache.updateMessage(event.message.id, event.message)
    }

    override fun onMessageDelete(event: DeckMessageDeleteEvent) {
        cache.updateMessage(event.messageId, null)
    }

    override fun onMemberUpdate(event: DeckMemberUpdateEvent) {
        val inCacheUser = cache.retrieveUser(event.user.id)
        val inCacheMembers = cache.retrieveAllMembersOfId(event.user.id)
        if (inCacheUser != null)
            cache.updateUser(event.user.id, DeckUser(
                client = event.client,
                id = inCacheUser.id,
                name = event.patch.name or inCacheUser.name,
                subdomain = inCacheUser.subdomain,
                avatar = event.patch.avatar nullableOr inCacheUser.avatar,
                banner = event.patch.banner nullableOr inCacheUser.banner,
                aboutInfo = DeckUserAboutInfo(
                    biography = event.patch.biography nullableOr inCacheUser.aboutInfo.biography,
                    tagline = event.patch.tagline nullableOr inCacheUser.aboutInfo.tagline,
                ),
                creationTime = inCacheUser.creationTime,
                lastLoginTime = inCacheUser.lastLoginTime
            ))
        inCacheMembers.forEach { member ->
            if (member.id != event.user.id) return@forEach
            val team = member.team
            cache.updateMember(id = member.id, team.id, DeckMember(
                client = event.client,
                id = member.id,
                name = event.patch.name or member.name,
                nickname = event.patch.nickname nullableOr member.nickname,
                avatar = event.patch.avatar nullableOr member.avatar,
                user = member.user,
                team = member.team
            ))
        }
    }

    override fun onMemberLeave(event: DeckMemberLeaveEvent) {
        cache.updateUser(event.user.id, null)
        cache.updateMember(event.user.id, event.team.id, null)
    }

    override fun onTeamChannelCreate(event: DeckTeamChannelCreateEvent) {
        cache.updateChannel(event.channel.id, event.channel)
    }

    override fun onTeamChannelUpdate(event: DeckTeamChannelUpdateEvent) {
        cache.updateChannel(event.channel.id, event.channel)
    }

    override fun onTeamChannelDelete(event: DeckTeamChannelDeleteEvent) {
        cache.updateChannel(event.channelId, null)
    }

    override fun onTeamChannelsDelete(event: DeckTeamChannelsDeleteEvent) {
        for (channelId in event.channels.keys) {
            cache.updateChannel(channelId, null)
        }
    }
}