package com.guildedkt.entity

import com.guildedkt.util.GenericId
import com.guildedkt.util.GuildedUnknown
import kotlinx.serialization.Serializable

@Serializable
@GuildedUnknown
data class RawRole(
    val id: GenericId
)
