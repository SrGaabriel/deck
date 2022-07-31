package io.github.deck.core.entity

import io.github.deck.common.entity.RawServerMemberSummary
import io.github.deck.common.entity.UserType
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.common.util.asNullable
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.impl.DeckMember
import io.github.deck.core.stateless.StatelessMember
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant

/**
 * This represents a guilded user.
 * This interface's default implementation is [DeckMember].
 */
public interface Member: StatelessMember {
    /** The user's name, not to be confused with his [nickname] */
    public val name: String
    /** Whether this member is a bot or an actual user */
    public val type: UserType

    /** A stateless user instance of this user */
    public val user: StatelessUser get() = BlankStatelessUser(client, id)

    /** This user's avatar, null if not set (default doggo avatar) */
    public val avatar: String?
    /** This user's banner, null if not set */
    public val banner: String?

    /** The user's nickname in this server */
    public val nickname: String?
    /** A list of all role ids assigned to this member */
    public val roleIds: List<IntGenericId>

    /** True if this member is the server's owner */
    public val isOwner: Boolean

    /** The instant the user was registered in guilded */
    public val createdAt: Instant
    /** The instant the user joined the server */
    public val joinedAt: Instant

    public fun asSummary(): MemberSummary =
        MemberSummary(client, id, serverId, name, type, avatar, roleIds)
}

public data class MemberSummary(
    val client: DeckClient,
    val id: GenericId,
    val serverId: GenericId,
    val name: String,
    val type: UserType,
    val avatar: String?,
    val roleIds: List<IntGenericId>,
) {
    public companion object {
        public fun from(client: DeckClient, serverId: GenericId, raw: RawServerMemberSummary): MemberSummary = MemberSummary(
            client = client,
            id = raw.user.id,
            name = raw.user.name,
            type = raw.user.type,
            avatar = raw.user.avatar.asNullable(),
            serverId = serverId,
            roleIds = raw.roleIds
        )
    }

    public suspend fun getMember(): Member =
        DeckMember.from(client, serverId, client.rest.server.getServerMember(serverId, id))
}