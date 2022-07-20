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

    public suspend fun connect()

    public suspend fun startHeartbeat(): Job

    public suspend fun startListening(): Job

    public suspend fun disconnect(expectingReconnect: Boolean = false)
}

/**
 * Instead of starting listening and sending heartbeats in [Gateway.connect],
 * we assign another function to bear this responsibility, since we don't want it handling multiple tasks.
 *
 * Calls [Gateway.connect], [Gateway.startListening], [Gateway.startHeartbeat] in order.
 */
public suspend fun Gateway.start() {
    connect()
    startListening()
    startHeartbeat()
}