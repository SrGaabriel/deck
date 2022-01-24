package com.deck.core

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationResult
import com.deck.common.util.DeckExperimental
import com.deck.core.cache.CacheManager
import com.deck.core.cache.DeckCacheManager
import com.deck.core.delegator.DeckEntityDelegator
import com.deck.core.delegator.DeckEntityStrategizer
import com.deck.core.delegator.EntityDelegator
import com.deck.core.delegator.EntityStrategizer
import com.deck.core.event.DefaultEventService
import com.deck.core.event.EventService
import com.deck.core.module.GatewayModule
import com.deck.core.module.RestModule
import com.deck.core.service.AuthService
import com.deck.core.service.DefaultAuthService
import com.deck.core.util.WrappedEventSupplier
import com.deck.core.util.WrappedEventSupplierData
import com.deck.core.util.setAuthentication
import com.deck.gateway.util.EventSupplier
import com.deck.gateway.util.EventSupplierData

public class DeckClient(
    private val auth: Authentication,
    public val rest: RestModule,
    public val gateway: GatewayModule
) : EventSupplier, WrappedEventSupplier {
    @DeckExperimental
    public var eventService: EventService = DefaultEventService(this)

    public var authenticationService: AuthService = DefaultAuthService(rest.authRoute)
    public var authenticationResults: AuthenticationResult? = null

    override val eventSupplierData: EventSupplierData by gateway::eventSupplierData
    override val wrappedEventSupplierData: WrappedEventSupplierData by eventService::wrappedEventSupplierData

    public val entityStrategizer: EntityStrategizer = DeckEntityStrategizer(this)
    public val entityCacheManager : CacheManager = DeckCacheManager()
    public val entityDelegator: EntityDelegator = DeckEntityDelegator(rest, entityStrategizer, entityCacheManager)

    public suspend fun login() {
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