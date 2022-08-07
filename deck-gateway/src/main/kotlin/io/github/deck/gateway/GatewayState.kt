package io.github.deck.gateway

public enum class GatewayState {
    Active,
    Retrying,
    Replaying,
    Closed
}