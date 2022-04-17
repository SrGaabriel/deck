package io.github.deck.extras.conversation

import io.github.deck.common.util.DeckExperimental
import io.github.deck.core.entity.Message
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.rest.builder.SendMessageRequestBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

@DeckExperimental
public interface Conversation: CoroutineScope {
    public val active: Boolean
    public val listeningJob: Job
    public val responseFlow: Flow<Message>

    public val user: StatelessUser

    public fun init()

    public fun end()

    public suspend fun ask(message: Message): ConversationMessage

    public suspend fun ask(message: SendMessageRequestBuilder.() -> Unit): ConversationMessage
}