package io.github.deck.gateway

import io.github.deck.gateway.event.GatewayEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

/**
 * Base gateway interface
 */
public interface Gateway {
    public val id: Int

    public var state: GatewayState
    public val scope: CoroutineScope
    public val eventFlow: SharedFlow<GatewayEvent>

    // Null if not yet received
    public var lastMessageId: String?

    /**
     * Connects to the gateway, but doesn't start listening yet
     *
     * @see start
     * @see startListening
     * @see startPinging
     */
    public suspend fun connect()

    /**
     * Awaits for the Hello event and then starts sending heartbeats (pings)
     * accordingly
     */
    public suspend fun startPinging(): Job

    /**
     * Starts listening to the websocket events
     */
    public suspend fun startListening(): Job

    /**
     * Disconnects from the gateway. Set [expectingReconnect] to true to send
     * close reason of `SERVICE_RESTART`
     */
    public suspend fun disconnect(expectingReconnect: Boolean = false)
}

/**
 * Connects to the gateway, starts listening to its events and then starts to send pings to it.
 *
 * Calls [Gateway.connect], [Gateway.startListening], [Gateway.startPinging] respectively.
 */
public suspend fun Gateway.start() {
    connect()
    startListening()
    startPinging()
}