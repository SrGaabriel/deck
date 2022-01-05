package com.guildedkt

import com.guildedkt.util.UniqueId
import java.util.*

fun UniqueId.mapToBuiltin(): UUID =
    UUID.fromString(raw)
