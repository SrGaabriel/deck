package com.deck.gateway.com.deck.gateway.entity

import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.Timestamp
import com.deck.common.util.UniqueId
import kotlinx.serialization.Serializable

/**
 * All parameters are missing except for [enabled] when bot is enabled/disabled.
 * All parameters are missing except for [teamId], [deletedAt] and [userId] when bot is deleted.
 */
@Serializable
data class RawPartialBot(
    val id: UniqueId,
    val enabled: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val error: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val botId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val teamId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val createdBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val createdAt: OptionalProperty<Timestamp> = OptionalProperty.NotPresent,
    val deletedAt: OptionalProperty<Timestamp?> = OptionalProperty.NotPresent,
    val triggerType: OptionalProperty<String> = OptionalProperty.NotPresent,
    // val triggerMeta: OptionalProperty<RawBotFlowMeta> = OptionalProperty.NotPresent,
    val userId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val actionType: OptionalProperty<String> = OptionalProperty.NotPresent,
    // val actionMeta: OptionalProperty<RawBotFlowMeta> = OptionalProperty.NotPresent
)