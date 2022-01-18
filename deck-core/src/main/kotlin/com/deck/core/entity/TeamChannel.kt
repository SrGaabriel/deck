package com.deck.core.entity

import com.deck.common.util.GenericId

public interface TeamChannel : Channel {
    public val teamId: GenericId
}
