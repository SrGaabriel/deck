package io.github.deck.core.event

import io.github.deck.core.DeckClient
import io.github.deck.core.event.list.*
import io.github.deck.core.event.message.messageCreateEvent
import io.github.deck.core.event.message.messageDeleteEvent
import io.github.deck.core.event.message.messageUpdateEvent
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
        registerMapper(listItemCompleteEvent)
        registerMapper(listItemCreateEvent)
        registerMapper(listItemUpdateEvent)
        registerMapper(listItemDeleteEvent)
        registerMapper(listItemUncompleteEvent)
        registerMapper(messageCreateEvent)
        registerMapper(messageDeleteEvent)
        registerMapper(messageUpdateEvent)
        registerMapper(memberBanEvent)
        registerMapper(memberJoinEvent)
        registerMapper(memberLeaveEvent)
        registerMapper(memberUnbanEvent)
        registerMapper(memberUpdateEvent)
        registerMapper(serverXpAddEvent)
        registerMapper(helloEvent)
        registerMapper(webhookCreateEvent)
        registerMapper(webhookUpdateEvent)
    }

    override fun listen(): Job = client.gateway.on<GatewayEvent> {
        val event: DeckEvent = mappers[this::class]?.map(client, this) ?: return@on
        eventWrappingFlow.emit(event)
    }

    @Suppress("unchecked_cast")
    private inline fun <reified T : GatewayEvent> registerMapper(mapper: EventMapper<out T, out DeckEvent>) {
        if (mappers.containsKey(T::class))
            error("Tried to register duplicate event mapper. If you wish to override an event, try creating your own implementation of the EventService interface")
        mappers[T::class] = mapper as EventMapper<GatewayEvent, DeckEvent>
    }
}

public fun <F : GatewayEvent, T : DeckEvent> EventService.mapper(mapper: EventMapper<F, T>): EventMapper<F, T> = mapper

public fun interface EventMapper<F : GatewayEvent, T : DeckEvent> {
    public suspend fun map(client: DeckClient, event: F): T?
}