package io.github.deck.core.stateless

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.core.entity.Documentation
import io.github.deck.core.entity.impl.DeckDocumentation
import io.github.deck.core.stateless.channel.StatelessDocumentationChannel
import io.github.deck.core.util.BlankStatelessDocumentationChannel
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.rest.builder.CreateDocumentationRequestBuilder
import java.util.*

public interface StatelessDocumentation: StatelessEntity {
    public val id: IntGenericId
    public val channelId: UUID
    public val serverId: GenericId

    public val channel: StatelessDocumentationChannel get() = BlankStatelessDocumentationChannel(client, channelId, serverId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public suspend fun update(builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation =
        DeckDocumentation.from(client, client.rest.channel.updateDocumentation(channelId, id, builder))

    public suspend fun delete(): Unit =
        client.rest.channel.deleteDocumentation(channelId, id)
}