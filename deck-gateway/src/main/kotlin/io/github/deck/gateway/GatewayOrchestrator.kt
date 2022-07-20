package io.github.deck.gateway

import io.github.deck.common.log.DeckLogger
import io.github.deck.common.util.MicroutilsLogger
import io.github.deck.gateway.event.GatewayEvent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext

public class GatewayOrchestrator(internal val token: String): CoroutineScope {
    override val coroutineContext: CoroutineContext = Executors
        .newFixedThreadPool(8)
        .asCoroutineDispatcher()
    internal val httpClient = HttpClient(CIO.create()) {
        install(WebSockets)
    }
    public val globalEventsFlow: MutableSharedFlow<GatewayEvent> = MutableSharedFlow()
    public var logger: DeckLogger = MicroutilsLogger("Gateway Logger")

    public var logEventPayloads: Boolean = false
    public val gateways: MutableMap<Int, Gateway> = mutableMapOf()

    public var enableEventReplaying: Boolean = true

    private var gatewayIds = AtomicInteger(0)

    /**
     * Creates and returns a gateway without starting it
     *
     * @return the created gateway
     */
    public fun createGateway(): Gateway {
        val gateway = DefaultGateway(
            id = gatewayIds.getAndIncrement(),
            orchestrator = this
        )
        gateways[gateway.id] = gateway
        return gateway
    }

    /**
     * Starts the gateway
     *
     * @param id gateway id
     */
    public suspend fun startGateway(id: Int) {
        gateways[id]?.start()
    }

    /**
     * Disconnects from the gateway without ditching it.
     *
     * @param id gateway id
     * @param expectingReconnect set to true to disconnect with `SERVICE_RESTART` code
     */
    public suspend fun disconnectGateway(id: Int, expectingReconnect: Boolean = false) {
        val gateway = gateways[id] ?: return
        gateway.disconnect(expectingReconnect)
    }

    /**
     * Disconnects from the gateway and ditches it.
     *
     * @param id gateway id
     */
    public suspend fun ditchGateway(id: Int) {
        disconnectGateway(id)
        gateways.remove(id)
    }
}