package io.github.deck.core.event

import io.github.deck.core.DeckClient
import io.github.deck.core.event.calendar.calendarEventCreateEvent
import io.github.deck.core.event.calendar.calendarEventDeleteEvent
import io.github.deck.core.event.calendar.calendarEventUpdateEvent
import io.github.deck.core.event.channel.serverChannelCreateEvent
import io.github.deck.core.event.channel.serverChannelDeleteEvent
import io.github.deck.core.event.channel.serverChannelUpdateEvent
import io.github.deck.core.event.documentation.documentationCreateEvent
import io.github.deck.core.event.documentation.documentationDeleteEvent
import io.github.deck.core.event.documentation.documentationUpdateEvent
import io.github.deck.core.event.list.*
import io.github.deck.core.event.message.*
import io.github.deck.core.event.server.*
import io.github.deck.core.event.user.helloEvent
import io.github.deck.core.event.webhook.webhookCreateEvent
import io.github.deck.core.event.webhook.webhookUpdateEvent
import io.github.deck.core.util.WrappedEventSupplier
import io.github.deck.core.util.WrappedEventSupplierData
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.util.on
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlin.reflect.KClass

public interface DeckEvent {
    public val client: DeckClient
    public val gatewayId: Int
}

public interface EventService : WrappedEventSupplier {
    public val eventWrappingFlow: SharedFlow<DeckEvent>

    public fun ready()

    public fun listen(): Job
}

public class DefaultEventService(private val client: DeckClient) : EventService {
    override val eventWrappingFlow: MutableSharedFlow<DeckEvent> = MutableSharedFlow()

    override val wrappedEventSupplierData: WrappedEventSupplierData = WrappedEventSupplierData(
        scope = client.gateway,
        sharedFlow = eventWrappingFlow
    )
    @PublishedApi
    internal val mappers: MutableMap<KClass<out GatewayEvent>, EventMapper<GatewayEvent, DeckEvent>> = mutableMapOf()

    override fun ready() {
        registerMapper(serverChannelCreateEvent)
        registerMapper(serverChannelUpdateEvent)
        registerMapper(serverChannelDeleteEvent)
        registerMapper(listItemCompleteEvent)
        registerMapper(listItemCreateEvent)
        registerMapper(listItemUpdateEvent)
        registerMapper(listItemDeleteEvent)
        registerMapper(listItemUncompleteEvent)
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
    }

    override fun listen(): Job = client.gateway.on<GatewayEvent> {
        val event: DeckEvent = mappers[this::class]?.map(client, this) ?: return@on
        eventWrappingFlow.emit(event)
    }

    @Suppress("unchecked_cast")
    private inline fun <reified T : GatewayEvent> registerMapper(mapper: EventMapper<out T, out DeckEvent>) {
        val castedMapper = mapper as EventMapper<GatewayEvent, DeckEvent>
        if (mappers.containsKey(T::class) || mappers.containsValue(castedMapper))
            error("Tried to register duplicate event mapper for event '${T::class::simpleName}'. If you wish to override an event, try creating your own implementation of the EventService interface")
        mappers[T::class] = castedMapper
    }
}

@Suppress("unused")
public fun <F : GatewayEvent, T : DeckEvent> EventService.mapper(mapper: EventMapper<F, T>): EventMapper<F, T> = mapper

public fun interface EventMapper<F : GatewayEvent, T : DeckEvent> {
    public suspend fun map(client: DeckClient, event: F): T?
}