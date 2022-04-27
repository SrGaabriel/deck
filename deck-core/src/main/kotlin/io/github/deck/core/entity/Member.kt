package io.github.deck.core.entity

import io.github.deck.common.entity.UserType
import io.github.deck.common.util.IntGenericId
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

    /** The instant the user was registered in guilded */
    public val createdAt: Instant
    /** The instant the user joined the server */
    public val joinedAt: Instant
}

public interface MemberSummary: StatelessMember {
    /** The user's name, not to be confused with his nickname (which is not available information) */
    public val name: String
    /** Whether this member is a bot or an actual user */
    public val type: UserType

    /** This user's avatar url, null if not set (default doggo avatar) */
    public val avatar: String?

    /** A stateless user instance of this user */
    public val user: StatelessUser get() = BlankStatelessUser(client, id)

    /** A list of all role ids assigned to this member */
    public val roleIds: List<IntGenericId>

    public suspend fun getMember(): Member
}