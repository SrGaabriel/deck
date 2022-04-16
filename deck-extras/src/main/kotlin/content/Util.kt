package com.deck.extras.content

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