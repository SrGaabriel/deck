package com.deck.core.entity

import com.deck.common.util.GenericId

interface TeamChannel: Channel {
    val teamId: GenericId
}
