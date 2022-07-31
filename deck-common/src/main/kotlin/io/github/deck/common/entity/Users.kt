package io.github.deck.common.entity

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.OptionalProperty
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
public class RawUserId(public val id: String)

@Serializable
public enum class SocialLinkType(public val id: String) {
    @SerialName("twitch")
    Twitch("twitch"),
    @SerialName("bnet")
    BattleNet("bnet"),
    @SerialName("psn")
    PlaystationNetwork("psn"),
    @SerialName("xbox")
    Xbox("xbox"),
    @SerialName("steam")
    Steam("steam"),
    @SerialName("origin")
    Origin("origin"),
    @SerialName("youtube")
    Youtube("youtube"),
    @SerialName("twitter")
    Twitter("twitter"),
    @SerialName("facebook")
    Facebook("facebook"),
    @SerialName("switch")
    Switch("switch"),
    @SerialName("patreon")
    Patreon("patreon"),
    @SerialName("roblox")
    Roblox("roblox");
}