package com.deck.extras.conversation.type

import com.deck.core.event.message.DeckMessageCreateEvent
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.core.util.on
import com.deck.extras.conversation.AbstractMessageChannelConversation
import kotlinx.coroutines.Job

public class DefaultMessageChannelConversation(
    override val user: StatelessUser,
    override val messageChannel: StatelessMessageChannel
): AbstractMessageChannelConversation() {
    override var active: Boolean = false
    override lateinit var listeningJob: Job

    override fun init() {
        active = true
        listeningJob = messageChannel.client.on<DeckMessageCreateEvent> {
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

public suspend fun StatelessUser.conversate(
    channel: StatelessMessageChannel,
    scope: suspend AbstractMessageChannelConversation.() -> Unit
) {
    val conversation = DefaultMessageChannelConversation(this, channel)
    conversation.init()
    scope(conversation)
    conversation.end()
}