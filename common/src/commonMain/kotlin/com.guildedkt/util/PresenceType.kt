package com.guildedkt.util

import kotlinx.serialization.Serializable

@Serializable(PresenceType.Serializer::class)
enum class PresenceType(val rawName: String) {
    Game("gamepresence");

    companion object Serializer: StringIdEnumSerializer<PresenceType>(
        StringSerializationStrategy(values().associateBy { it.rawName })
    )
}