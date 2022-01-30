package com.deck.core.proxy

import com.deck.common.entity.RawDocumentation
import com.deck.common.entity.RawListItem
import com.deck.common.entity.RawMessage
import com.deck.core.entity.Documentation
import com.deck.core.entity.ListItem
import com.deck.core.entity.Message

public interface EntityDecoder {
    public fun decodeMessage(raw: RawMessage): Message

    public fun decodeListItem(raw: RawListItem): ListItem

    public fun decodeDocumentation(raw: RawDocumentation): Documentation
}