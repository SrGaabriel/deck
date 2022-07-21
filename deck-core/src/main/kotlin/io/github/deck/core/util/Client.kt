package io.github.deck.core.util

import io.github.deck.common.log.warning
import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Server
import io.github.deck.core.entity.channel.Channel
import io.github.deck.core.entity.channel.ServerChannel
import io.github.deck.core.entity.impl.DeckServer
import io.github.deck.core.entity.impl.DeckServerChannel
import io.github.deck.gateway.GatewayOrchestrator
import io.github.deck.rest.RestClient
import io.github.deck.rest.builder.CreateChannelRequestBuilder
import java.util.*

public class ClientBuilder(private val token: String) {
    public var rest: RestClient = RestClient(token)
    public var gateway: GatewayOrchestrator = GatewayOrchestrator(token)

    public var logRequests: Boolean by rest::logRequests
    public var logEventPayloads: Boolean by gateway::logEventPayloads

    public var enableEventReplaying: Boolean by gateway::enableEventReplaying

    /**
     * Enables request (including responses) and event payloads logging
     */
    public fun debugMode() {
        logRequests = true
        logEventPayloads = true
    }

    public fun build(): DeckClient {
        if (!token.startsWith("gapi_")) {
            rest.logger.warning { "Your token does not start with 'gapi_', meaning it is either invalid or outdated." }
        }
        return DeckClient(rest, gateway)
    }
}

public suspend fun DeckClient.getServer(serverId: GenericId): Server =
    DeckServer.from(this, rest.server.getServer(serverId))

public suspend fun DeckClient.createChannel(builder: CreateChannelRequestBuilder.() -> Unit): Channel =
    DeckServerChannel.from(this, rest.channel.createChannel(builder))

public suspend fun DeckClient.getChannel(channelId: UUID): Channel =
    DeckServerChannel.from(this, rest.channel.retrieveChannel(channelId))

@Suppress("unchecked_cast")
public suspend fun <T : ServerChannel> DeckClient.getChannelOf(channelId: UUID): T =
    (DeckServerChannel.from(this, rest.channel.retrieveChannel(channelId)) as? T) ?: error("Called 'getChannelOf' with the wrong channel type")

public suspend fun DeckClient.deleteChannel(channelId: UUID): Unit =
    rest.channel.deleteChannel(channelId)