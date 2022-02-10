package com.deck.core.event

import com.deck.core.entity.User
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.on
import kotlinx.coroutines.Job

public interface UserEvent: DeckEvent {
    public val user: StatelessUser

    public suspend fun getUser(): User = user.getState()
}

public inline fun <reified T : UserEvent> StatelessUser.observe(crossinline scope: suspend T.() -> Unit): Job =
    client.on<T> {
        if (user.id == this@observe.id) scope(this)
    }