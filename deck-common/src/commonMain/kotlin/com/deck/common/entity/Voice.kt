package com.deck.common.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class RawBroadcastCallResponse {
    @SerialName("hangup")
    HANGUP,

    @SerialName("accepted")
    ACCEPTED;
}