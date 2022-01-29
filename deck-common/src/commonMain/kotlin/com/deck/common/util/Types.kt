package com.deck.common.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class SocialLinkType {
    @SerialName("twitch")
    TWITCH,
    @SerialName("bnet")
    BATTLE_NET,
    @SerialName("psn")
    PLAYSTATION_NETWORK,
    @SerialName("xbox")
    XBOX,
    @SerialName("steam")
    STEAM,
    @SerialName("origin")
    ORIGIN,
    @SerialName("youtube")
    YOUTUBE,
    @SerialName("twitter")
    TWITTER,
    @SerialName("facebook")
    FACEBOOK,
    @SerialName("switch")
    SWITCH,
    @SerialName("patreon")
    PATREON,
    @SerialName("roblox")
    ROBLOX;
}