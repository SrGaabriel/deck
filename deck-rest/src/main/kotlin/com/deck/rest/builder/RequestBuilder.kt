package com.deck.rest.builder

import com.deck.common.content.Content
import com.deck.common.content.ContentBuilder

/**
 * Those builders structures are **HEAVILY** inspired on Kord's
 * request builders.
 */
public interface RequestBuilder<T> {
    public fun toRequest(): T
}

public interface MutableContentHolder {
    public var content: Content

    public operator fun Content.invoke(builder: ContentBuilder.() -> Unit) {
        this@MutableContentHolder.content = ContentBuilder().apply(builder).build()
    }
}