package com.deck.rest.builder

/**
 * Those builders structures are **HEAVILY** inspired on Kord's
 * request builders.
 */
public interface RequestBuilder<T> {
    public fun toRequest(): T
}