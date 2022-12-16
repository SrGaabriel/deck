package io.github.srgaabriel.deck.common.entity

import io.github.srgaabriel.deck.common.util.IntGenericId
import kotlinx.serialization.Serializable

@Serializable
public data class RawEmote(
    val id: IntGenericId,
    val name: String,
    val url: String
)