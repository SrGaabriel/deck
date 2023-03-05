package io.github.srgaabriel.deck.core.event

import io.github.srgaabriel.deck.common.log.warning
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.event.calendar.*
import io.github.srgaabriel.deck.core.event.channel.serverChannelCreate
import io.github.srgaabriel.deck.core.event.channel.serverChannelDelete
import io.github.srgaabriel.deck.core.event.channel.serverChannelUpdate
import io.github.srgaabriel.deck.core.event.documentation.*
import io.github.srgaabriel.deck.core.event.forum.*
import io.github.srgaabriel.deck.core.event.list.*
import io.github.srgaabriel.deck.core.event.message.*
import io.github.srgaabriel.deck.core.event.server.*
import io.github.srgaabriel.deck.core.event.user.hello
import io.github.srgaabriel.deck.core.event.webhook.webhookCreate
import io.github.srgaabriel.deck.core.event.webhook.webhookUpdate
import io.github.srgaabriel.deck.gateway.Gateway
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayHelloEvent
import io.github.srgaabriel.deck.gateway.util.on
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
        registerMapper(serverChannelCreate)
        registerMapper(serverChannelUpdate)
        registerMapper(serverChannelDelete)
        registerMapper(listItemComplete)
        registerMapper(listItemCreate)
        registerMapper(listItemUpdate)
        registerMapper(listItemDelete)
        registerMapper(listItemIncomplete)
        registerMapper(documentationCreate)
        registerMapper(documentationUpdate)
        registerMapper(documentationDelete)
        registerMapper(messageCreate)
        registerMapper(messageDelete)
        registerMapper(messageUpdate)
        registerMapper(memberBan)
        registerMapper(memberJoin)
        registerMapper(memberLeave)
        registerMapper(memberUnban)
        registerMapper(memberUpdate)
        registerMapper(hello)
        registerMapper(webhookCreate)
        registerMapper(webhookUpdate)
        registerMapper(calendarEventCreate)
        registerMapper(calendarEventUpdate)
        registerMapper(calendarEventDelete)
        registerMapper(messageReactionAdd)
        registerMapper(messageReactionRemove)
        registerMapper(calendarEventRsvpUpdate)
        registerMapper(calendarEventRsvpBulkUpdate)
        registerMapper(calendarEventRsvpDelete)
        registerMapper(forumTopicCreate)
        registerMapper(forumTopicUpdate)
        registerMapper(forumTopicDelete)
        registerMapper(forumTopicPin)
        registerMapper(forumTopicUnpin)
        registerMapper(forumTopicLocked)
        registerMapper(forumTopicUnlocked)
        registerMapper(forumTopicCommentCreate)
        registerMapper(forumTopicCommentUpdate)
        registerMapper(forumTopicCommentDelete)
        registerMapper(botMembershipCreated)
        registerMapper(botMembershipDelete)
        registerMapper(serverRolesUpdate)
        registerMapper(serverRolesUpdate)
        registerMapper(forumTopicReactionAdd)
        registerMapper(forumTopicReactionRemove)
        registerMapper(forumTopicCommentReactionAdd)
        registerMapper(forumTopicCommentReactionRemove)
        registerMapper(calendarEventReactionAdd)
        registerMapper(calendarEventReactionRemove)
        registerMapper(calendarEventCommentReactionAdd)
        registerMapper(calendarEventCommentReactionRemove)
        registerMapper(documentationReactionAdd)
        registerMapper(documentationReactionRemove)
        registerMapper(documentationCommentReactionAdd)
        registerMapper(documentationCommentReactionRemove)
        registerMapper(memberSocialLinkCreate)
        registerMapper(memberSocialLinkUpdate)
        registerMapper(memberSocialLinkDelete)
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