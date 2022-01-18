package com.deck.gateway.entity

import com.deck.common.util.GenericId
import kotlinx.serialization.Serializable

@Serializable
public data class RawUserIdObject(
    val id: GenericId
)