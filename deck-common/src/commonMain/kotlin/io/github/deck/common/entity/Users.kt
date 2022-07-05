package io.github.deck.common.entity

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.OptionalProperty
import io.github.deck.common.util.SocialLinkType
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class RawUser(
    val id: GenericId,
    val type: UserType = UserType.USER,
    val name: String,
    val avatar: OptionalProperty<String> = OptionalProperty.NotPresent,
    val banner: OptionalProperty<String> = OptionalProperty.NotPresent,
    val createdAt: Instant
)

@Serializable
public data class RawUserSummary(
    val id: GenericId,
    val type: UserType = UserType.USER,
    val name: String,
    val avatar: OptionalProperty<String> = OptionalProperty.NotPresent,
)

@Serializable
public data class RawUserSocialLink(
    val handle: OptionalProperty<String> = OptionalProperty.NotPresent,
    val serviceId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val type: SocialLinkType
)

@Serializable
public enum class UserType {
    @SerialName("user")
    USER,
    @SerialName("bot")
    BOT
}

@Serializable
public value class RawUserId(public val id: String)