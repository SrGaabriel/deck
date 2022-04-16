package com.deck.extras.content

import com.deck.core.entity.Message
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.extras.content.node.Node
import com.deck.extras.content.node.encode
import com.deck.rest.builder.SendMessageRequestBuilder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

public interface Mentionable {
    public fun getMentionNode(): Node
}

// workaround, TODO fix
private val ENCODING_JSON = Json { encodeDefaults = false }

public fun SendMessageRequestBuilder.content(builder: ContentBuilder.() -> Unit) {
    contentElement = ENCODING_JSON.encodeToJsonElement(ContentBuilder().apply(builder).build().encode())
}

public suspend fun StatelessMessageChannel.sendContent(builder: ContentBuilder.() -> Unit): Message =
    sendMessage { content(builder) }

public suspend fun StatelessMessage.sendReplyOfContent(builder: ContentBuilder.() -> Unit): Message =
    sendReply { content(builder) }