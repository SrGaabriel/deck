package com.deck.core

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationResult
import com.deck.common.util.DeckExperimental
import com.deck.core.event.DefaultEventService
import com.deck.core.event.EventService
import com.deck.core.module.GatewayModule
import com.deck.core.module.RestModule
import com.deck.core.service.AuthService
import com.deck.core.service.DefaultAuthService
import com.deck.core.service.DefaultUserService
import com.deck.core.service.UserService
import com.deck.core.util.WrappedEventSupplier
import com.deck.core.util.WrappedEventSupplierData
import com.deck.core.util.setAuthentication
import com.deck.gateway.util.EventSupplier

class DeckClient(
    private val auth: Authentication,
    val rest: RestModule,
    val gateway: GatewayModule
): EventSupplier, WrappedEventSupplier {
    @DeckExperimental
    var eventService: EventService = DefaultEventService(this)

    var authenticationService: AuthService = DefaultAuthService(rest.authRoute)
    var authenticationResults: AuthenticationResult? = null

    var userService: UserService = DefaultUserService(rest.userRoute)

    override val eventSupplierData by gateway::eventSupplierData
    override val wrappedEventSupplierData: WrappedEventSupplierData by eventService::wrappedEventSupplierData

    suspend fun login() {
        this.setAuthentication(authenticationService.login(auth))
        openAllTeamGateways()
        gateway.start()
        eventService.startListeningAndConveying()
    }

    private suspend fun openAllTeamGateways() {
        val self = rest.userRoute.getSelf()
        for (team in self.teams) {
            gateway.openGateway(team.id)
        }
    }
}
