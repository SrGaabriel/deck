package io.github.deck.core.event

import io.github.deck.common.log.warning
import io.github.deck.core.DeckClient
import io.github.deck.core.event.calendar.*
import io.github.deck.core.event.channel.serverChannelCreateEvent
import io.github.deck.core.event.channel.serverChannelDeleteEvent
import io.github.deck.core.event.channel.serverChannelUpdateEvent
import io.github.deck.core.event.documentation.documentationCreateEvent
import io.github.deck.core.event.documentation.documentationDeleteEvent
import io.github.deck.core.event.documentation.documentationUpdateEvent
import io.github.deck.core.event.forum.*
import io.github.deck.core.event.list.*
import io.github.deck.core.event.message.*
import io.github.deck.core.event.server.*
import io.github.deck.core.event.user.helloEvent
import io.github.deck.core.event.webhook.webhookCreateEvent
import io.github.deck.core.event.webhook.webhookUpdateEvent
import io.github.deck.gateway.Gateway
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayHelloEvent
import io.github.deck.gateway.util.on
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlin.reflect.KClass

public interface DeckEvent {
    public val client: DeckClient
    public val barebones: GatewayEvent

    public val gatewayOrNull: Gateway? get() = client.gateway.gateways[barebones.info.gatewayId]
    public val gateway: Gateway get() = gatewayOrNull ?: error("Gateway was probably closed and can't be accessed anymore.")
}

public interface EventService {
    public val eventWrappingFlow: SharedFlow<DeckEvent>

    public fun listen(): Job

    public suspend fun dispatch(event: DeckEvent)
}

public class DefaultEventService(private val client: DeckClient) : EventService {
    override val eventWrappingFlow: MutableSharedFlow<DeckEvent> = MutableSharedFlow()

    private val mappers: MutableMap<KClass<out GatewayEvent>, EventMapper<GatewayEvent, DeckEvent>> = mutableMapOf()

    init {
        registerMapper(serverChannelCreateEvent)
        registerMapper(serverChannelUpdateEvent)
        registerMapper(serverChannelDeleteEvent)
        registerMapper(listItemCompleteEvent)
        registerMapper(listItemCreateEvent)
        registerMapper(listItemUpdateEvent)
        registerMapper(listItemDeleteEvent)
        registerMapper(listItemIncompleteEvent)
        registerMapper(documentationCreateEvent)
        registerMapper(documentationUpdateEvent)
        registerMapper(documentationDeleteEvent)
        registerMapper(messageCreateEvent)
        registerMapper(messageDeleteEvent)
        registerMapper(messageUpdateEvent)
        registerMapper(memberBanEvent)
        registerMapper(memberJoinEvent)
        registerMapper(memberLeaveEvent)
        registerMapper(memberUnbanEvent)
        registerMapper(memberUpdateEvent)
        registerMapper(helloEvent)
        registerMapper(webhookCreateEvent)
        registerMapper(webhookUpdateEvent)
        registerMapper(calendarEventCreateEvent)
        registerMapper(calendarEventUpdateEvent)
        registerMapper(calendarEventDeleteEvent)
        registerMapper(messageReactionAddEvent)
        registerMapper(messageReactionRemoveEvent)
        registerMapper(calendarEventRsvpUpdateEvent)
        registerMapper(calendarEventRsvpBulkUpdateEvent)
        registerMapper(calendarEventRsvpDeleteEvent)
        registerMapper(forumTopicCreateEvent)
        registerMapper(forumTopicUpdateEvent)
        registerMapper(forumTopicDeleteEvent)
        registerMapper(forumTopicPinEvent)
        registerMapper(forumTopicUnpinEvent)
        registerMapper(forumTopicLockedEvent)
        registerMapper(forumTopicUnlockedEvent)
        registerMapper(forumTopicCommentCreateEvent)
        registerMapper(forumTopicCommentUpdateEvent)
        registerMapper(forumTopicCommentDeleteEvent)
        registerMapper(botMembershipCreated)
        registerMapper(botMembershipDelete)
        registerMapper(serverRolesUpdate)
    }

    override fun listen(): Job = client.gateway.on<GatewayEvent> {
        if (this is GatewayHelloEvent) {
            client._selfId = self.id
        }
        val mapped = mappers[this::class]?.map(client, this) ?: return@on
        dispatch(mapped)
    }

    override suspend fun dispatch(event: DeckEvent) {
        eventWrappingFlow.emit(event)
    }

    @Suppress("unchecked_cast")
    private inline fun <reified T : GatewayEvent> registerMapper(mapper: EventMapper<out T, out DeckEvent>) {
        val castedMapper = mapper as EventMapper<GatewayEvent, DeckEvent>
        if (mappers.containsKey(T::class) || mappers.containsValue(castedMapper)) {
            client.gateway.logger.warning {
                "There were two or more attempts to register an event mapper for event `${T::class.simpleName}`. Deck is now overwriting the previous mapper for the new one."
            }
        }
        mappers[T::class] = castedMapper
    }
}

@Suppress("UnusedReceiverParameter")
public fun <F : GatewayEvent, T : DeckEvent> EventService.mapper(mapper: EventMapper<F, T>): EventMapper<F, T> = mapper

public fun interface EventMapper<F : GatewayEvent, T : DeckEvent> {
    public suspend fun map(client: DeckClient, event: F): T?
}