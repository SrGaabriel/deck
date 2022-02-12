package com.deck.core.util

import com.github.benmanes.caffeine.cache.Cache

internal fun <K, V> Cache<K, V>.putOrInvalidate(key: K, value: V?) {
    return if (value == null) invalidate(key)
    else put(key, value)
}