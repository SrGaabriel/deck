package com.deck.core

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationResult
import com.deck.core.module.GatewayModule
import com.deck.core.module.RestModule
import com.deck.core.service.AuthenticationService
import com.deck.core.service.DefaultAuthenticationService
import com.deck.core.util.setAuthentication

class DeckClient(
    private val auth: Authentication,
    val rest: RestModule,
    val gateway: GatewayModule
) {
    var authenticationService: AuthenticationService = DefaultAuthenticationService(rest)
    var authenticationResults: AuthenticationResult? = null

    suspend fun login() {
        this.setAuthentication(authenticationService.login(auth))
        openAllTeamGateways()
        gateway.start()
    }

    private suspend fun openAllTeamGateways() {
        val self = rest.userRoute.getSelf()
        for (team in self.teams) {
            gateway.openGateway(team.id)
        }
    }
}
