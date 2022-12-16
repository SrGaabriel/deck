package io.github.srgaabriel.deck.core.util

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Server
import io.github.srgaabriel.deck.core.entity.channel.Channel
import io.github.srgaabriel.deck.core.entity.channel.ServerChannel
import io.github.srgaabriel.deck.core.entity.impl.DeckServer
import io.github.srgaabriel.deck.core.entity.impl.DeckServerChannel
import io.github.srgaabriel.deck.gateway.GatewayOrchestrator
import io.github.srgaabriel.deck.rest.RestClient
import io.github.srgaabriel.deck.rest.builder.CreateChannelRequestBuilder
import java.util.*

/**
 * A builder for a [DeckClient]
 */
public class ClientBuilder(token: String) {
    public var rest: RestClient = RestClient(token)
    public var gateway: GatewayOrchestrator = GatewayOrchestrator(token)

    public var logRequests: Boolean by rest::logRequests
    public var logEventPayloads: Boolean by gateway::logEventPayloads

    public var enableEventReplaying: Boolean by gateway::enableEventReplaying
    public var automaticPrivateRepliesToPrivateMessages: Boolean? = null

    /**
     * Enables request (including responses) and event payloads logging
     */
    public fun debugMode() {
        logRequests = true
        logEventPayloads = true
    }

    public fun build(): DeckClient {
        val client = DeckClient(rest, gateway)
        if (automaticPrivateRepliesToPrivateMessages != null)
            client.privateRepliesToPrivateMessagesByDefault = automaticPrivateRepliesToPrivateMessages!!
        return client
    }
}

public suspend fun DeckClient.getServer(serverId: GenericId): Server =
    DeckServer.from(this, rest.server.getServer(serverId))

public suspend fun DeckClient.createChannel(builder: CreateChannelRequestBuilder.() -> Unit): Channel =
    DeckServerChannel.from(this, rest.channel.createChannel(builder))

public suspend fun DeckClient.getChannel(channelId: UUID): Channel =
    DeckServerChannel.from(this, rest.channel.getChannel(channelId))

@Suppress("unchecked_cast")
public suspend fun <T : ServerChannel> DeckClient.getChannelOf(channelId: UUID): T =
    (DeckServerChannel.from(this, rest.channel.getChannel(channelId)) as? T) ?: error("Called 'getChannelOf' with the wrong channel type")

public suspend fun DeckClient.deleteChannel(channelId: UUID): Unit =
    rest.channel.deleteChannel(channelId)