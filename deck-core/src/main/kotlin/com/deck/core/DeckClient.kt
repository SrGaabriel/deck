package com.deck.core

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationResult
import com.deck.common.util.GenericId
import com.deck.core.cache.CacheManager
import com.deck.core.cache.DeckCacheManager
import com.deck.core.cache.observer.CacheUpdater
import com.deck.core.cache.observer.DefaultCacheUpdater
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
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessUser
import com.deck.core.util.WrappedEventSupplier
import com.deck.core.util.WrappedEventSupplierData
import com.deck.gateway.util.EventSupplier
import com.deck.gateway.util.EventSupplierData
import kotlin.properties.Delegates

public class DeckClient(
    private val auth: Authentication,
    public val rest: RestModule,
    public val gateway: GatewayModule
) : EventSupplier, WrappedEventSupplier {
    public var eventService: EventService = DefaultEventService(this)

    public var authenticationService: AuthService = DefaultAuthService(this)
    public var authenticationResults: AuthenticationResult by Delegates.notNull()

    override val eventSupplierData: EventSupplierData by gateway::eventSupplierData
    override val wrappedEventSupplierData: WrappedEventSupplierData by eventService::wrappedEventSupplierData

    public val cache: CacheManager = DeckCacheManager()

    public val entityDecoder: EntityDecoder = DeckEntityDecoder(this)
    public val entityDelegator: EntityDelegator = DeckEntityDelegator(rest, entityDecoder, cache)

    public val cacheUpdater : CacheUpdater = DefaultCacheUpdater(this, cache, entityDecoder)

    public val selfId: GenericId by rest.restClient::selfId
    public val self: StatelessUser by lazy { BlankStatelessUser(this, selfId) }

    public suspend fun login() {
        authenticationResults = authenticationService.login(auth)
        gateway.openTeamGateways(*authenticationResults.self.teams.map { it.id }.toTypedArray())
        gateway.start()
        eventService.startListening()
    }
}