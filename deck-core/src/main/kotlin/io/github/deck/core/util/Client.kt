package io.github.deck.core.util

import io.github.deck.core.DeckClient
import io.github.deck.core.entity.channel.Channel
import io.github.deck.core.entity.channel.ServerChannel
import io.github.deck.core.entity.impl.DeckServerChannel
import io.github.deck.gateway.GatewayOrchestrator
import io.github.deck.rest.RestClient
import io.github.deck.rest.builder.CreateChannelRequestBuilder
import java.util.*

public class ClientBuilder(token: String) {
    public var rest: RestClient = RestClient(token)
    public var gateway: GatewayOrchestrator = GatewayOrchestrator(token)

    public fun build(): DeckClient =
        DeckClient(rest, gateway)
}

public suspend fun DeckClient.createChannel(builder: CreateChannelRequestBuilder.() -> Unit): Channel =
    DeckServerChannel.from(this, rest.channel.createChannel(builder))

public suspend fun DeckClient.getChannel(channelId: UUID): Channel =
    DeckServerChannel.from(this, rest.channel.retrieveChannel(channelId))

@Suppress("unchecked_cast")
public suspend fun <T : ServerChannel> DeckClient.getChannelOf(channelId: UUID): T =
    (DeckServerChannel.from(this, rest.channel.retrieveChannel(channelId)) as? T) ?: error("Called 'getChannelOf' with the wrong channel type")

public suspend fun DeckClient.deleteChannel(channelId: UUID): Unit =
    rest.channel.deleteChannel(channelId)