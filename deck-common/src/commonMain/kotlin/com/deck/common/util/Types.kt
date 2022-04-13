package com.deck.common.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
public enum class SocialLinkType(public val officialName: String) {
    Twitch("twitch"),
    BattleNet("bnet"),
    PlaystationNetwork("psn"),
    Xbox("xbox"),
    Steam("steam"),
    Origin("origin"),
    Youtube("youtube"),
    Twitter("twitter"),
    Facebook("facebook"),
    Switch("switch"),
    Patreon("patreon"),
    Roblox("roblox");
}

public object SocialLinkTypeSerializer: KSerializer<SocialLinkType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("social_link", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): SocialLinkType = decoder.decodeString().let { socialLinkId ->
        SocialLinkType.values().first { it.officialName == socialLinkId }
    }

    override fun serialize(encoder: Encoder, value: SocialLinkType): Unit = encoder.encodeString(value.officialName)
}