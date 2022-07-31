package io.github.deck.common.util

import java.util.*

public fun UniqueId: UUID =
    UUID.fromString(raw)

public fun UUID.mapToModel(): UniqueId =
    UniqueId(toString())