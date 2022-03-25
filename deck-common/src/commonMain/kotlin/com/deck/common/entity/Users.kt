package com.deck.common.entity

import com.deck.common.util.GenericId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class RawUserSummary(
    val id: GenericId,
    val type: RawUserType = RawUserType.USER,
    val name: String
)

@Serializable
public enum class RawUserType {
    @SerialName("user")
    USER,
    @SerialName("bot")
    BOT
}