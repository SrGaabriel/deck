package io.github.deck.extras.misc

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.core.event.message.MessageReactionAddEvent
import io.github.deck.core.stateless.StatelessMessage
import kotlinx.coroutines.Job

/**
 * @param scope Where you'll handle the reaction event, return true to continue listening
 * to [MessageReactionAddEvent] or false to cancel the job.
 */

public suspend fun StatelessMessage.onReaction(scope: suspend MessageReactionAddEvent.() -> Boolean): Job {
    var job: Job? = null
    job = client.on<MessageReactionAddEvent> {
        if (messageId != this@onReaction.id)
            return@on
        if (!scope())
            job?.cancel()
    }
    return job
}

/**
 * @param userId The user you are expecting to react, leave null if there's
 * none particularly. If this user is defined and someone else reacts to the message besides him, it'll be simply ignored.
 *
 * @param reactionId The reaction you are expecting to get on your message, leave null if there's
 * none particularly. If this reaction is defined and the user reacts with another emote besides it, it'll be simply ignored.
 *
 * @param scope Where you'll handle the event when it's already known that both the provided [userId] and
 * [reactionId] are expected and correct.
 */
public suspend fun StatelessMessage.onReaction(
    userId: GenericId? = null,
    reactionId: IntGenericId? = null,
    scope: suspend MessageReactionAddEvent.() -> Boolean
): Job = onReaction onUncheckedReaction@ {
    if (userId != null && userId != this.userId)
        return@onUncheckedReaction true
    else if (reactionId != null && reactionId != emote.id)
        return@onUncheckedReaction true
    scope()
}