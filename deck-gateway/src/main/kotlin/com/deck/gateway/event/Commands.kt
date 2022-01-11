package com.deck.gateway.event

import com.deck.common.util.UniqueId
import kotlinx.serialization.Serializable

interface GatewayCommand

@Serializable
data class GatewayTypingCommand(
    val channelId: UniqueId
): GatewayCommand