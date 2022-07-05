package io.github.deck.common.entity

import io.github.deck.common.util.IntGenericId
import kotlinx.serialization.Serializable

@Serializable
public data class RawEmote(
    val id: IntGenericId,
    val name: String,
    val url: String
)