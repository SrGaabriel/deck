package io.github.deck.core.util

import io.github.deck.common.util.DeckUnsupported
import io.github.deck.common.util.Emoji
import io.github.deck.common.util.IntGenericId

public interface ReactionHolder {
    public suspend fun addReaction(reactionId: IntGenericId)

    @DeckUnsupported
    public suspend fun removeReaction(reactionId: IntGenericId)
}

public suspend fun ReactionHolder.addReaction(emoji: Emoji): Unit = addReaction(emoji.id)

@DeckUnsupported
public suspend fun ReactionHolder.removeReaction(emoji: Emoji): Unit = removeReaction(emoji.id)