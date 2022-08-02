package io.github.deck.core.util

import io.github.deck.core.event.DeckEvent
import io.github.deck.gateway.util.onEachHandlingErrors
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

public inline fun <reified T : DeckEvent> on(
    gatewayId: Int?,
    scope: CoroutineScope,
    eventsFlow: SharedFlow<DeckEvent>,
    noinline callback: suspend T.() -> Unit
): Job = eventsFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>()
    .filter { it.barebones.info.gatewayId == (gatewayId ?: return@filter true) }
    .onEachHandlingErrors(callback)
    .launchIn(scope)

@OptIn(ExperimentalCoroutinesApi::class)
public suspend inline fun <reified T : DeckEvent> await(
    gatewayId: Int?,
    scope: CoroutineScope,
    eventsFlow: SharedFlow<DeckEvent>,
    timeout: Long = 4000
): T? = withTimeoutOrNull(timeout) {
    suspendCancellableCoroutine<T> { continuation ->
        scope.launch {
            val event = eventsFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>()
                .filter { it.barebones.info.gatewayId == (gatewayId ?: return@filter true) }.first()
            continuation.resume(event) {}
        }
    }
}