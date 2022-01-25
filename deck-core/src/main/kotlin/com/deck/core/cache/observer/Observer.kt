package com.deck.core.cache.observer

import com.deck.common.util.DeckExperimental
import com.deck.core.cache.CacheManager
import com.deck.core.delegator.EntityDecoder
import com.deck.core.event.DeckEvent
import com.deck.core.event.channel.DeckTeamChannelCreateEvent
import com.deck.core.event.channel.DeckTeamChannelDeleteEvent
import com.deck.core.event.channel.DeckTeamChannelUpdateEvent
import com.deck.core.event.channel.DeckTeamChannelsDeleteEvent
import com.deck.core.util.WrappedEventSupplier
import com.deck.core.util.on
import kotlinx.coroutines.Job

public interface CacheEntityObserver {
    public val supplier: WrappedEventSupplier
    public val cache: CacheManager
    public val decoder: EntityDecoder

    public fun onTeamChannelCreate(event: DeckTeamChannelCreateEvent)
    public fun onTeamChannelUpdate(event: DeckTeamChannelUpdateEvent)
    public fun onTeamChannelDelete(event: DeckTeamChannelDeleteEvent)
    public fun onTeamChannelsDelete(event: DeckTeamChannelsDeleteEvent)

    @OptIn(DeckExperimental::class)
    public fun startObserving(): Job = supplier.on<DeckEvent> {
        when (this) {
            is DeckTeamChannelCreateEvent -> onTeamChannelCreate(this)
            is DeckTeamChannelUpdateEvent -> onTeamChannelUpdate(this)
            is DeckTeamChannelDeleteEvent -> onTeamChannelDelete(this)
            is DeckTeamChannelsDeleteEvent -> onTeamChannelsDelete(this)
        }
    }
}

public class DefaultCacheEntityObserver(
    override val supplier: WrappedEventSupplier,
    override val cache: CacheManager,
    override val decoder: EntityDecoder
): CacheEntityObserver {
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