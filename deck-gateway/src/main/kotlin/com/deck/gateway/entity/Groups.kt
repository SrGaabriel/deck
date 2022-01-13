package com.deck.gateway.com.deck.gateway.entity

import com.deck.common.util.GenericId
import kotlinx.serialization.Serializable

@Serializable
data class RawGroupIdObject(
    val id: GenericId
)