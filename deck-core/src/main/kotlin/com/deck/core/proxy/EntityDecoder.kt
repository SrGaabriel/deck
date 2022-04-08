package com.deck.core.proxy

import com.deck.common.entity.*
import com.deck.core.entity.*
import com.deck.core.entity.channel.ForumThread

public interface EntityDecoder {
    public fun decodeMessage(raw: RawMessage): Message

    public fun decodeBan(raw: RawServerBan): ServerBan

    public fun decodeWebhook(raw: RawWebhook): Webhook

    public fun decodeListItem(raw: RawListItem): ListItem

    public fun decodeForumThread(raw: RawForumThread): ForumThread

    public fun decodeDocumentation(raw: RawDocumentation): Documentation
}