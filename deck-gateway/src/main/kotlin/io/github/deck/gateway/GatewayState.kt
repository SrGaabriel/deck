package io.github.deck.gateway

public sealed class GatewayState(public val listening: Boolean) {
    public object Active : GatewayState(true)

    public object Replaying : GatewayState(true)

    public object Closed : GatewayState(false)
}