package io.github.srgaabriel.deck.core

import io.github.srgaabriel.deck.common.log.warning
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.entity.Server
import io.github.srgaabriel.deck.core.entity.User
import io.github.srgaabriel.deck.core.entity.channel.Channel
import io.github.srgaabriel.deck.core.entity.channel.ServerChannel
import io.github.srgaabriel.deck.core.entity.impl.DeckServer
import io.github.srgaabriel.deck.core.entity.impl.DeckServerChannel
import io.github.srgaabriel.deck.core.entity.impl.DeckUser
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.DefaultEventService
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import io.github.srgaabriel.deck.core.util.ClientBuilder
import io.github.srgaabriel.deck.gateway.GatewayOrchestrator
import io.github.srgaabriel.deck.rest.RestClient
import io.github.srgaabriel.deck.rest.builder.CreateChannelRequestBuilder
import io.github.srgaabriel.deck.rest.builder.ExecuteWebhookRequestBuilder
import io.github.srgaabriel.deck.rest.request.ExecuteWebhookResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Symbolizes a bot as a whole, with a [gateway] and a [rest] client
 *
 * @param rest rest client
 * @param gateway gateway manager
 */
public class DeckClient internal constructor(
    public val rest: RestClient,
    public val gateway: GatewayOrchestrator,
) {
    public var eventService: DefaultEventService = DefaultEventService(this)

    internal var _selfId: GenericId? = null
    public val selfId: GenericId get() = _selfId ?: error("Tried to access self id before client was connected")
    public val self: StatelessUser by lazy { BlankStatelessUser(this, selfId) }

    /**
     * Starts the gateway
     */
    public suspend fun login() {
        if (!rest.token.startsWith("gapi_"))
            rest.logger.warning { "Your token does not start with 'gapi_', meaning it is either invalid or outdated." }
        val masterGateway = gateway.createGateway()
        eventService.listen()
        masterGateway.start()
    }

    public suspend fun getSelf(): User =
        getUser("@me")

    public suspend fun getUser(userId: GenericId): User =
        DeckUser.from(this, rest.user.getUser(userId))

    public suspend fun getServer(serverId: GenericId): Server =
        DeckServer.from(this, rest.server.getServer(serverId))

    public suspend fun createChannel(builder: CreateChannelRequestBuilder.() -> Unit): Channel =
        DeckServerChannel.from(this, rest.channel.createChannel(builder))

    public suspend fun getChannel(channelId: UUID): Channel =
        DeckServerChannel.from(this, rest.channel.getChannel(channelId))

    @Suppress("unchecked_cast")
    public suspend fun <T : ServerChannel> getChannelOf(channelId: UUID): T =
        (DeckServerChannel.from(this, rest.channel.getChannel(channelId)) as? T) ?: error("Called 'getChannelOf' with the wrong channel type")

    public suspend fun deleteChannel(channelId: UUID): Unit =
        rest.channel.deleteChannel(channelId)

    public companion object {
        /**
         * Builds a client with the provided [token] and settings provided in the [builder].
         *
         * @param token bot's token
         * @param builder client builder
         *
         * @return built client
         */
        public operator fun invoke(token: String, builder: ClientBuilder.() -> Unit = {}): DeckClient =
            ClientBuilder(token).apply(builder).build()
    }

    public var privateRepliesToPrivateMessagesByDefault: Boolean = true

    /**
     * Executes the webhook with the provided [webhookId] and [webhookToken], sending a message according to the [builder] values
     *
     * @param webhookId webhook's id
     * @param webhookToken webhook's token
     * @param builder message builder
     *
     * @return response
     */
    @OptIn(ExperimentalContracts::class)
    public suspend fun executeWebhook(webhookId: UUID, webhookToken: String, builder: ExecuteWebhookRequestBuilder.() -> Unit): ExecuteWebhookResponse {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return rest.webhook.executeWebhook(webhookId, webhookToken, builder)
    }

    public inline fun <reified T : DeckEvent> on(
        gatewayId: Int? = null,
        scope: CoroutineScope = gateway,
        noinline callback: suspend T.() -> Unit
    ): Job = io.github.srgaabriel.deck.core.util.on(gatewayId, scope, eventService.eventWrappingFlow, callback)

    public suspend inline fun <reified T : DeckEvent> await(
        timeout: Long,
        gatewayId: Int? = null,
        scope: CoroutineScope = gateway,
    ): T? = io.github.srgaabriel.deck.core.util.await(timeout, gatewayId, scope, eventService.eventWrappingFlow)
}