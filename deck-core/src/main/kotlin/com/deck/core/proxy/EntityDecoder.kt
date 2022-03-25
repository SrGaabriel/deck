package com.deck.core.proxy

import com.deck.common.entity.*
import com.deck.core.entity.Documentation
import com.deck.core.entity.ListItem
import com.deck.core.entity.Message
import com.deck.core.entity.ServerBan
import com.deck.core.entity.channel.ForumThread

public interface EntityDecoder {
    public fun decodeMessage(raw: RawMessage): Message

    public fun decodeBan(raw: RawServerBan): ServerBan

    public fun decodeListItem(raw: RawListItem): ListItem

    public fun decodeForumThread(raw: RawForumThread): ForumThread

    public fun decodeDocumentation(raw: RawDocumentation): Documentation
}