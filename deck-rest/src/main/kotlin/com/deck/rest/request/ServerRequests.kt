package com.deck.rest.request

import com.deck.common.entity.RawServerBan

public data class GetServerBansResponse(
    val serverMemberBans: List<RawServerBan>
)