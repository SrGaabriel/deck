package com.deck.core.entity.impl

import com.deck.common.util.IntGenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Documentation
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessDocumentationChannel
import com.deck.rest.builder.CreateDocumentationRequestBuilder
import kotlinx.datetime.Instant

public data class DeckDocumentation(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val title: String,
    override val content: String,
    override val server: StatelessServer,
    override val channel: StatelessDocumentationChannel,
    override val createdAt: Instant,
    override val updatedAt: Instant,
    override val createdBy: StatelessUser,
    override val updatedBy: StatelessUser,
): Documentation {
    override suspend fun update(builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation {
        return super.update {
            title = this@DeckDocumentation.title
            content = this@DeckDocumentation.content
            builder(this)
        }
    }
}