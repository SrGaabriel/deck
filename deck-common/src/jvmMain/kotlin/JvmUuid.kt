package com.deck.common.util

import java.util.*

fun UniqueId.mapToBuiltin(): UUID =
    UUID.fromString(raw)

fun UUID.mapToModel(): UniqueId =
    UniqueId(toString())