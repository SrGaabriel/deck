package com.deck.gateway

import com.deck.gateway.event.GatewayEvent
import com.deck.gateway.util.EventSupplier
import com.deck.gateway.util.EventSupplierData
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

public class GatewayOrchestrator(private val token: String): EventSupplier, CoroutineScope {
    override val coroutineContext: CoroutineContext = Executors
        .newFixedThreadPool(4)
        .asCoroutineDispatcher()
    private val httpClient = HttpClient(CIO.create()) {
        install(WebSockets)
    }
    private val globalEventsFlow: MutableSharedFlow<GatewayEvent> = MutableSharedFlow()
    override val eventSupplierData: EventSupplierData by lazy {
        EventSupplierData(
            scope = this,
            sharedFlow = globalEventsFlow
        )
    }

    public var debugPayloads: Boolean = false
    public val gateways: MutableList<Gateway> = mutableListOf()

    public fun openGateway(): Gateway = DefaultGateway(
        token = token,
        debugPayloads = debugPayloads,
        gatewayId = gateways.size,
        scope = this,
        client = httpClient,
        eventSharedFlow = globalEventsFlow
    ).also { gateways.add(it) }

    public suspend fun closeGateway(gateway: Gateway): Unit =
        gateways.remove(gateway.also { gateway.disconnect(false) }).let {}
}