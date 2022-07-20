package io.github.deck.extras.conversation.type

import io.github.deck.common.util.DeckExperimental
import io.github.deck.core.entity.Message
import io.github.deck.core.event.message.MessageCreateEvent
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.stateless.channel.StatelessMessageChannel
import io.github.deck.core.util.on
import io.github.deck.core.util.sendMessage
import io.github.deck.core.util.update
import io.github.deck.extras.conversation.AbstractMessageChannelConversation
import io.github.deck.extras.conversation.ConversationMessage
import kotlinx.coroutines.Job

@DeckExperimental
public class DefaultMessageChannelConversation(
    override val user: StatelessUser,
    override val messageChannel: StatelessMessageChannel
): AbstractMessageChannelConversation() {
    override var active: Boolean = false
    override lateinit var listeningJob: Job

    override fun init() {
        active = true
        listeningJob = messageChannel.client.on<MessageCreateEvent> {
            if (author.id != user.id)
                return@on
            if (channel.id != messageChannel.id)
                return@on
            if (message.content == abortMessage)
                return@on end()
            responseFlow.emit(message)
        }
    }

    override fun end(): Unit = listeningJob.cancel().also { active = false }
}

@DeckExperimental
public class DefaultMessageEditingChannelConversation(
    override val user: StatelessUser,
    override val messageChannel: StatelessMessageChannel
): AbstractMessageChannelConversation() {
    override var active: Boolean = false
    override lateinit var listeningJob: Job

    private var firstMessage: Message? = null

    override fun init() {
        active = true
        listeningJob = messageChannel.client.on<MessageCreateEvent> {
            if (author.id != user.id)
                return@on
            if (channel.id != messageChannel.id)
                return@on
            if (message.content == abortMessage)
                return@on end()
            responseFlow.emit(message)
        }
    }

    override suspend fun ask(content: String): ConversationMessage {
        if (firstMessage == null)
            firstMessage = messageChannel.sendMessage(content)
        else
            firstMessage?.update(content)
        return ask(firstMessage!!)
    }

    override fun end(): Unit = listeningJob.cancel().also { active = false }
}

@DeckExperimental
public suspend fun StatelessUser.conversate(
    channel: StatelessMessageChannel,
    scope: suspend AbstractMessageChannelConversation.() -> Unit
) {
    val conversation = DefaultMessageChannelConversation(this, channel)
    conversation.init()
    scope(conversation)
    conversation.end()
}