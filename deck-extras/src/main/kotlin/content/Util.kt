package io.github.srgaabriel.deck.extras.content

import io.github.srgaabriel.deck.core.entity.Message
import io.github.srgaabriel.deck.core.stateless.StatelessMessage
import io.github.srgaabriel.deck.core.stateless.channel.StatelessMessageChannel
import io.github.srgaabriel.deck.extras.content.node.encode
import io.github.srgaabriel.deck.rest.builder.SendMessageRequestBuilder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

// workaround, TODO fix
private val ENCODING_JSON = Json { encodeDefaults = false }

public fun SendMessageRequestBuilder.content(builder: ContentBuilder.() -> Unit) {
    contentElement = ENCODING_JSON.encodeToJsonElement(ContentBuilder().apply(builder).build().encode())
}

public suspend fun StatelessMessageChannel.sendContent(builder: ContentBuilder.() -> Unit): Message =
    sendMessage { content(builder) }

public suspend fun StatelessMessage.sendReplyOfContent(builder: ContentBuilder.() -> Unit): Message =
    sendReply { content(builder) }