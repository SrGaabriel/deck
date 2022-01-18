package com.deck.common.util

import java.util.*

public fun UniqueId.mapToBuiltin(): UUID =
    UUID.fromString(raw)

public fun UUID.mapToModel(): UniqueId =
    UniqueId(toString())