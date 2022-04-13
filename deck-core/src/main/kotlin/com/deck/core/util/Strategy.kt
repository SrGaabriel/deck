package com.deck.core.util

import com.deck.core.DeckClient

public interface EntityStrategy<R, W> {
    public fun strategize(client: DeckClient, raw: R): W
}