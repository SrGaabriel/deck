package io.github.srgaabriel.deck.core.entity

import io.github.srgaabriel.deck.common.entity.ServerType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant
import java.util.*

/**
 * Symbolizes a server (not a group) in Guilded
 */
public interface Server: StatelessServer {
    /** Server owner's id */
    public val ownerId: GenericId
    public val owner: StatelessUser get() = BlankStatelessUser(client, ownerId)

    /** Server type, null if not defined */
    public val type: ServerType?
    /** Server name */
    public val name: String

    /** Server about/description */
    public val about: String?

    /** Server avatar, null if default */
    public val avatar: String?
    /** Server banner, null if none */
    public val banner: String?

    /** Server timezone, null if not defined */
    public val timezone: String?
    /** Server default channel id (used for welcome messages), null if none */
    public val defaultChannelId: UUID?

    /** Instant the server was created */
    public val createdAt: Instant
}