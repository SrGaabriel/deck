package com.deck.gateway.event

import com.deck.common.util.UniqueId
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface GatewayCommand

interface CommandEncoder {
    fun encodeCommandToString(command: GatewayCommand): String
}

val strictJson = Json { encodeDefaults = false; explicitNulls = true }

@Serializable
data class GatewayTypingCommand(
    val channelId: UniqueId
): GatewayCommand

class DefaultCommandEncoder: CommandEncoder {
    override fun encodeCommandToString(command: GatewayCommand) = when(command) {
        is GatewayTypingCommand -> encode("ChatChannelTyping", command)
        else -> error("Attempt to send unsupported command")
    }

    private inline fun <reified T> encode(name: String, objekt: T) =
        """42["$name", ${strictJson.encodeToString(objekt)}]"""
}