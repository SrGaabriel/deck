package com.deck.core.util

import com.deck.common.util.DeckUnsupported
import com.deck.common.util.Emoji
import com.deck.common.util.IntGenericId

public interface ReactionHolder {
    public suspend fun addReaction(reactionId: IntGenericId)

    @DeckUnsupported
    public suspend fun removeReaction(reactionId: IntGenericId)
}

public suspend fun ReactionHolder.addReaction(emoji: Emoji): Unit = addReaction(emoji.id)

@DeckUnsupported
public suspend fun ReactionHolder.removeReaction(emoji: Emoji): Unit = removeReaction(emoji.id)