package com.deck.common.entity

import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.SocialLinkType
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class RawUser(
    val id: GenericId,
    val type: UserType,
    val name: String,
    val createdAt: Instant
)

@Serializable
public data class RawUserSummary(
    val id: GenericId,
    val type: UserType = UserType.USER,
    val name: String
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