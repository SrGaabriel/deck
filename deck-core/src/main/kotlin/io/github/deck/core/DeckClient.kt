package io.github.deck.core

import io.github.deck.common.log.warning
import io.github.deck.common.util.GenericId
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.DefaultEventService
import io.github.deck.core.util.ClientBuilder
import io.github.deck.gateway.GatewayOrchestrator
import io.github.deck.gateway.start
import io.github.deck.rest.RestClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

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

    private var _selfId: GenericId? = null
    public val selfId: GenericId get() = _selfId ?: error("Tried to access self id before client was connected")

    /**
     * Starts the gateway
     */
    public suspend fun login() {
        if (!rest.token.startsWith("gapi_"))
            rest.logger.warning { "Your token does not start with 'gapi_', meaning it is either invalid or outdated." }
        val masterGateway = gateway.createGateway()
        eventService.let {
            it.ready()
            it.listen()
        }
        masterGateway.start()
    }

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

    public inline fun <reified T : DeckEvent> on(
        scope: CoroutineScope = eventService,
        gatewayId: Int? = null,
        noinline callback: suspend T.() -> Unit
    ): Job = io.github.deck.core.util.on(gatewayId, scope, eventService.eventWrappingFlow, callback)

    public suspend inline fun <reified T : DeckEvent> await(
        timeout: Long = 4000,
        scope: CoroutineScope = eventService,
        gatewayId: Int? = null
    ): T? = io.github.deck.core.util.await(gatewayId, scope, eventService.eventWrappingFlow, timeout)
}