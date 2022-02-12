package com.deck.core.util

import com.deck.common.util.Emoji
import com.deck.core.stateless.StatelessMessage

public suspend fun StatelessMessage.addReaction(emoji: Emoji): Unit =
    addReaction(reactionId = emoji.id)

public suspend fun StatelessMessage.removeReaction(emoji: Emoji): Unit =
    removeReaction(reactionId = emoji.id)