package com.deck.core

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationResult
import com.deck.common.util.DeckExperimental
import com.deck.common.util.GenericId
import com.deck.core.cache.CacheManager
import com.deck.core.cache.DeckCacheManager
import com.deck.core.cache.observer.CacheEntityObserver
import com.deck.core.cache.observer.DefaultCacheEntityObserver
import com.deck.core.delegator.DeckEntityDecoder
import com.deck.core.delegator.DeckEntityDelegator
import com.deck.core.delegator.EntityDecoder
import com.deck.core.delegator.EntityDelegator
import com.deck.core.event.DefaultEventService
import com.deck.core.event.EventService
import com.deck.core.module.GatewayModule
import com.deck.core.module.RestModule
import com.deck.core.service.AuthService
import com.deck.core.service.DefaultAuthService
import com.deck.core.util.WrappedEventSupplier
import com.deck.core.util.WrappedEventSupplierData
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

    public val entityDecoder: EntityDecoder = DeckEntityDecoder(this)
    public val entityCacheManager : CacheManager = DeckCacheManager()
    public val entityCacheObserver : CacheEntityObserver = DefaultCacheEntityObserver(this, entityCacheManager, entityDecoder)
    public val entityDelegator: EntityDelegator = DeckEntityDelegator(rest, entityDecoder, entityCacheManager)

    public val selfId: GenericId by rest.restClient::selfId

    public suspend fun login() {
        authenticationResults = authenticationService.login(auth).also { result ->
            gateway.auth = result
            rest.restClient.token = result.token
            rest.restClient.selfId = result.self.user.id
        }
        val self = authenticationResults!!.self
        gateway.openTeamGateways(*self.teams.map { it.id }.toTypedArray())
        gateway.start()
        eventService.startListeningAndConveying()
        entityCacheObserver.startObserving()
    }
}