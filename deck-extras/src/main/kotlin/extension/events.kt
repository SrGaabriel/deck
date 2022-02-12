package com.deck.extras.extension

import com.deck.common.util.IntGenericId
import com.deck.core.entity.Message
import com.deck.core.event.message.DeckMessageReactionAddEvent
import com.deck.core.event.observe
import kotlinx.coroutines.Job
import kotlinx.coroutines.suspendCancellableCoroutine

public suspend fun Message.onReactionByAuthor(
    reactionId: IntGenericId? = null,
    cancelOnFirstTime: Boolean = false,
    scope: suspend DeckMessageReactionAddEvent.() -> Unit
): Job = suspendCancellableCoroutine {
    observe<DeckMessageReactionAddEvent> {
        if (this@observe.user.id != this@onReactionByAuthor.author.id)
            return@observe
        if (reactionId != null && reactionId != reaction.id)
            return@observe
        scope()
        if (cancelOnFirstTime) it.cancel()
    }
}