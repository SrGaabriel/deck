package com.deck.rest.builder

/**
 * Structures heavily inspired on the discord Kotlin
 * library Kord (see README.md)
 */
public interface RequestBuilder<T> {
    public fun toRequest(): T
}