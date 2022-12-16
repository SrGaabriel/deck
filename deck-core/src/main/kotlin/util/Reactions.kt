package io.github.srgaabriel.deck.core.util

import io.github.srgaabriel.deck.common.util.Emote
import io.github.srgaabriel.deck.common.util.IntGenericId

/**
 * Represents an entity who can have reactions
 */
public interface ReactionHolder {
    /**
     * Makes the bot add a reaction to this entity
     *
     * @param reactionId reaction emote id
     */
    public suspend fun addReaction(reactionId: IntGenericId)

    /**
     * Removes the bot's reaction from this entity
     *
     * @param reactionId reaction emote id
     */
    public suspend fun removeReaction(reactionId: IntGenericId)
}

/**
 * Makes the bot add a reaction to this entity
 *
 * @param emote reaction emote
 */
public suspend fun ReactionHolder.addReaction(emote: Emote): Unit = addReaction(emote.id)

/**
 * Removes the bot's reaction from this entity
 *
 * @param emote reaction emote
 */
public suspend fun ReactionHolder.removeReaction(emote: Emote): Unit = removeReaction(emote.id)