package com.deck.common.entity

import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
public data class RawServerBan(
    val user: RawUserSummary,
    val reason: OptionalProperty<String> = OptionalProperty.NotPresent,
    val createdBy: GenericId,
    val createdAt: Instant
)