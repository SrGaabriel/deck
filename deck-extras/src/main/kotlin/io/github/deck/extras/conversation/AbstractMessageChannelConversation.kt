package io.github.deck.extras.conversation

import io.github.deck.common.util.DeckExperimental
import io.github.deck.core.entity.Message
import io.github.deck.core.stateless.channel.StatelessMessageChannel
import io.github.deck.core.util.sendMessage
import io.github.deck.rest.builder.SendMessageRequestBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlin.coroutines.CoroutineContext

@DeckExperimental
public abstract class AbstractMessageChannelConversation: Conversation {
    override val coroutineContext: CoroutineContext = Dispatchers.Unconfined
    override val responseFlow: MutableSharedFlow<Message> = MutableSharedFlow()
    public abstract val messageChannel: StatelessMessageChannel

    public open var abortMessage: String = "abort"
    public open var onTimeout: suspend () -> Unit = { messageChannel.sendMessage("You were too late to answer, so this operation was aborted.") }

    override suspend fun ask(message: SendMessageRequestBuilder.() -> Unit): ConversationMessage =
        ask(messageChannel.sendMessage(message))

    override suspend fun ask(message: Message): ConversationMessage {
        if (!isActive) error("You need to init the conversation before asking something.")
        return ConversationMessage(
            conversation = this,
            replyingTo = message
        )
    }

    public suspend fun ask(content: String): ConversationMessage =
        ask(messageChannel.sendMessage(content))

    public fun onTimeout(callback: suspend () -> Unit) {
        this.onTimeout = callback
    }

    public suspend fun timeout(): Unit = onTimeout()
}