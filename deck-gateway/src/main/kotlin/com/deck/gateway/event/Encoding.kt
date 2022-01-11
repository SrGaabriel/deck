package com.deck.gateway.event

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val strictJson = Json { encodeDefaults = false; explicitNulls = true; isLenient = true }

interface CommandEncoder {
    fun encodeCommandToString(command: GatewayCommand): String
}

class DefaultCommandEncoder: CommandEncoder {
    override fun encodeCommandToString(command: GatewayCommand) = when(command) {
        is GatewayTypingCommand -> encode("ChatChannelTyping", command)
        else -> error("Attempt to send unsupported command")
    }

    private inline fun <reified T> encode(name: String, objekt: T) =
        """42["$name", ${strictJson.encodeToString(objekt)}]"""
}