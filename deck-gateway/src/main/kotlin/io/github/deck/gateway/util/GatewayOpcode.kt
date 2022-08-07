package io.github.deck.gateway.util

public object GatewayOpcode {
    public const val Dispatch: Int = 0
    public const val Hello: Int = 1
    public const val Resume: Int = 2
    public const val Error: Int = 8
    public const val InternalError: Int = 9
}