package com.deck.common.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RawUserPresenceType {
    @SerialName("gamepresence")
    Game;
}