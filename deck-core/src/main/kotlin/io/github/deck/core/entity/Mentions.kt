package io.github.deck.core.entity

import io.github.deck.common.entity.RawMessageMentions
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.common.util.asNullable
import io.github.deck.common.util.mapToBuiltin
import java.util.*

public data class Mentions(
    val users: List<GenericId>,
    val channels: List<UUID>,
    val roles: List<IntGenericId>,
    val here: Boolean,
    val everyone: Boolean,
) {
    public fun isEmpty(): Boolean =
        !everyone && !here && users.isEmpty() && channels.isEmpty() && roles.isEmpty()

    public companion object {
        public fun from(raw: RawMessageMentions): Mentions = Mentions(
            users = raw.users.asNullable().orEmpty().map { it.id },
            channels = raw.channels.asNullable().orEmpty().map { it.id.mapToBuiltin() },
            roles = raw.roles.asNullable().orEmpty().map { it.id },
            here = raw.here,
            everyone = raw.everyone,
        )
    }
}