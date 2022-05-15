package io.github.deck.common.entity

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.OptionalProperty
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
public data class RawServerMember(
    val user: RawUser,
    val roleIds: List<Int>,
    val nickname: OptionalProperty<String> = OptionalProperty.NotPresent,
    val joinedAt: Instant,
    val isOwner: Boolean = false
)

@Serializable
public data class RawServerMemberSummary(
    val user: RawUserSummary,
    val roleIds: List<Int>,
)

@Serializable
public data class RawServerBan(
    val user: RawUserSummary,
    val reason: OptionalProperty<String> = OptionalProperty.NotPresent,
    val createdBy: GenericId,
    val createdAt: Instant
)