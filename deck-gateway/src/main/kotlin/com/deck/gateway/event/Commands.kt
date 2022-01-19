package com.deck.gateway.event

import com.deck.common.util.UniqueId
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

public interface GatewayCommand

public interface CommandEncoder {
    public fun encodeCommandToString(command: GatewayCommand): String
}

@OptIn(ExperimentalSerializationApi::class)
public val strictJson: Json = Json { encodeDefaults = false; explicitNulls = true }

@Serializable
public data class GatewayTypingCommand(
    val channelId: UniqueId
) : GatewayCommand

public class DefaultCommandEncoder : CommandEncoder {
    override fun encodeCommandToString(command: GatewayCommand): String = when (command) {
        is GatewayTypingCommand -> encode("ChatChannelTyping", command)
        else -> error("Attempt to send unsupported command")
    }

    private inline fun <reified T> encode(name: String, objekt: T) =
        """42["$name", ${strictJson.encodeToString(objekt)}]"""
}