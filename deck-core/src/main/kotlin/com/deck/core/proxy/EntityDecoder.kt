package com.deck.core.proxy

import com.deck.common.entity.RawMessage
import com.deck.core.entity.Message

public interface EntityDecoder {
    public fun decodeMessage(raw: RawMessage): Message
}