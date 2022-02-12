package com.deck.core.event

import com.deck.common.util.DeckUnsupported
import com.deck.core.entity.Message
import com.deck.core.entity.User
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.on
import kotlinx.coroutines.Job

/**
 * Represents an event concerning/involving a user.
 *
 * @property user involved user
 */
public interface UserEvent: DeckEvent {
    public val user: StatelessUser

    public suspend fun getUser(): User = user.getState()
}

/**
 * Listen to all events concerning a user and filter them to the
 * specified user. In other words, simply listens to all events involving the specified user.
 *
 * @return observation job
 */
public inline fun <reified T : UserEvent> StatelessUser.observe(crossinline scope: suspend T.() -> Unit): Job =
    client.on<T> {
        if (user.id == this@observe.id) scope(this)
    }

/**
 * Represents an event concerning/involving a message.
 *
 * @property message involved message
 */
public interface MessageEvent: DeckEvent {
    public val message: StatelessMessage

    @DeckUnsupported
    public suspend fun getMessage(): Message = message.getState()
}

/**
 * Listen to all events concerning a message and filter them to the
 * specified message. In other words, simply listens to all events involving the specified message.
 *
 * @return observation job
 */
public inline fun <reified T : MessageEvent> StatelessMessage.observe(crossinline scope: suspend T.() -> Unit): Job =
    client.on<T> {
        if (message.id == this@observe.id) scope(this)
    }