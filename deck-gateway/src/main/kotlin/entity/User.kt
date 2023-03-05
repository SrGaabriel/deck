@file:UseSerializers(UUIDSerializer::class)

package io.github.srgaabriel.deck.gateway.entity

import io.github.srgaabriel.deck.common.entity.UserType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.OptionalProperty
import io.github.srgaabriel.deck.common.util.UUIDSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
public data class RawBot(
    val id: GenericId,
    val type: UserType = UserType.USER,
    val avatar: OptionalProperty<String> = OptionalProperty.NotPresent,
    val banner: OptionalProperty<String> = OptionalProperty.NotPresent,
    val botId: UUID,
    val name: String,
    val createdAt: Instant,
    val createdBy: GenericId
)

@Serializable
public data class RawServerMemberInfo(
    val id: GenericId,
    val nickname: OptionalProperty<String> = OptionalProperty.NotPresent
)

@Serializable
public data class RawServerRoleUpdate(
    val userId: GenericId,
    val roleIds: List<IntGenericId>
)