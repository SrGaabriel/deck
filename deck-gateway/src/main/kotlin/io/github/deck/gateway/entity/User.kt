@file:UseSerializers(UUIDSerializer::class)

package io.github.deck.gateway.entity

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.OptionalProperty
import io.github.deck.common.util.UUIDSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
public data class RawBot(
    val id: GenericId,
    val botId: UUID,
    val name: String,
    val createdAt: Instant,
    val createdBy: GenericId
)

@Serializable
public data class RawTeamMemberInfo(
    val id: GenericId,
    val nickname: OptionalProperty<String> = OptionalProperty.NotPresent
)