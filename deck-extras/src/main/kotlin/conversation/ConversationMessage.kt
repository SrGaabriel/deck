package com.deck.extras.conversation

import com.deck.core.entity.Message
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

public class ConversationMessage(
    public val conversation: Conversation,
    public val replyingTo: Message
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    public suspend fun awaitReply(timeout: Long): Message? = withTimeoutOrNull(timeout) {
        suspendCancellableCoroutine { continuation ->
            conversation.launch {
                val message = conversation.responseFlow.buffer(Channel.UNLIMITED).first { conversation.active }
                continuation.resume(message) {}
            }
        }
    }

    public fun onReply(callback: suspend (Message) -> Unit): Job =
        conversation.responseFlow.buffer(Channel.UNLIMITED).onEach(callback).launchIn(conversation)
}