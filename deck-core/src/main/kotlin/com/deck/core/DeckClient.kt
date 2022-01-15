package com.deck.core

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationResult
import com.deck.core.event.DefaultEventService
import com.deck.core.event.EventService
import com.deck.core.module.GatewayModule
import com.deck.core.module.RestModule
import com.deck.core.service.AuthenticationService
import com.deck.core.service.DefaultAuthenticationService
import com.deck.core.util.setAuthentication
import com.deck.gateway.util.EventSupplier

// TODO: Inherit [WrappedEventSupplier] instead of raw [EventSupplier]
class DeckClient(
    private val auth: Authentication,
    val rest: RestModule,
    val gateway: GatewayModule
): EventSupplier {
    @Deprecated("Not ready for use", replaceWith = ReplaceWith("gateway"))
    var eventService: EventService = DefaultEventService(gateway)

    var authenticationService: AuthenticationService = DefaultAuthenticationService(rest)
    var authenticationResults: AuthenticationResult? = null

    override val eventSupplierData by gateway::eventSupplierData

    suspend fun login() {
        this.setAuthentication(authenticationService.login(auth))
        openAllTeamGateways()
        gateway.start()
        // eventService.startListeningAndConveying()
    }

    private suspend fun openAllTeamGateways() {
        val self = rest.userRoute.getSelf()
        for (team in self.teams) {
            gateway.openGateway(team.id)
        }
    }
}
