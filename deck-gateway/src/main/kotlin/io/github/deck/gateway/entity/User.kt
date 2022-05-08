package io.github.deck.gateway.entity

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.OptionalProperty
import io.github.deck.common.util.UniqueId
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
public data class RawBot(
    val id: GenericId,
    val botId: UniqueId,
    val name: String,
    val createdAt: Instant,
    val createdBy: GenericId
)

@Serializable
public data class RawTeamMemberInfo(
    val id: GenericId,
    val nickname: OptionalProperty<String> = OptionalProperty.NotPresent
)