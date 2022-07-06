package io.github.deck.core.util

import io.github.deck.common.util.Emote
import io.github.deck.common.util.IntGenericId

public interface ReactionHolder {
    public suspend fun addReaction(reactionId: IntGenericId)

    public suspend fun removeReaction(reactionId: IntGenericId)
}

public suspend fun ReactionHolder.addReaction(emote: Emote): Unit = addReaction(emote.id)

public suspend fun ReactionHolder.removeReaction(emote: Emote): Unit = removeReaction(emote.id)