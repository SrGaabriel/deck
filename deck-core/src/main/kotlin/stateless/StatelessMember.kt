package io.github.srgaabriel.deck.core.stateless

import io.github.srgaabriel.deck.common.entity.SocialLinkType
import io.github.srgaabriel.deck.common.util.DeckDelicateApi
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.Ban
import io.github.srgaabriel.deck.core.entity.Member
import io.github.srgaabriel.deck.core.entity.SocialLink
import io.github.srgaabriel.deck.core.entity.impl.DeckMember
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.rest.util.GuildedRequestException

public interface StatelessMember: StatelessEntity {
    public val id: GenericId
    public val serverId: GenericId

    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * Sets the nickname for this member, or removes it if the provided
     * [nickname] is null.
     *
     * @param nickname new nickname, null to reset
     *
     * @return this member's new nickname
     */
    public suspend fun setNickname(nickname: String?): String? = when(nickname) {
        null -> client.rest.server.removeMemberNickname(id, serverId).let { null }
        else -> client.rest.server.updateMemberNickname(id, serverId, nickname)
    }

    /**
     * Assigns the specified role to this member
     *
     * @param roleId role to be assigned to this member
     */
    public suspend fun addRole(roleId: IntGenericId): Unit =
        client.rest.server.assignRoleToMember(serverId, id, roleId)

    /**
     * Retrieves a list of all the ids of the roles this member is assigned to
     *
     * @return list of all the ids of the roles this member is assigned to
     */
    public suspend fun getRoleIds(): List<IntGenericId> =
        client.rest.server.getMemberRoles(id, serverId)

    /**
     * Removes the specified role from this member
     *
     * @param roleId role to be removed from this member
     */
    public suspend fun removeRole(roleId: IntGenericId): Unit =
        client.rest.server.removeRoleFromMember(serverId, id, roleId)

    /**
     * Kicks this member from the server
     */
    public suspend fun kick(): Unit =
        client.rest.server.kickMember(id, serverId)

    /**
     * Bans this member from the server
     *
     * @param reason ban reason, null if none
     *
     * @return the ban
     */
    public suspend fun ban(reason: String? = null): Ban =
        Ban.from(client, client.rest.server.banMember(id, serverId, reason))

    @DeckDelicateApi
    /** @throws [GuildedRequestException] if not found */
    public suspend fun getBan(): Ban =
        Ban.from(client, client.rest.server.getMemberBan(id, serverId))

    /**
     * Unbans this member from the server.
     */
    public suspend fun unban(): Unit =
        client.rest.server.unbanMember(id, serverId)

    /**
     * Retrieves member's public social link of the provided type
     *
     * @param socialLinkType social link type
     *
     * @return found social link
     */
    public suspend fun getSocialLink(socialLinkType: SocialLinkType): SocialLink =
        SocialLink.from(client, client.rest.server.getMemberSocialLinks(id, serverId, socialLinkType))

    /**
     * Adds (not sets) xp to this member.
     *
     * @see setXp
     *
     * @param amount the amount of xp to be added
     * @return user's new xp
     */
    public suspend fun awardXp(amount: Int): Int =
        client.rest.server.awardXpToMember(serverId, id, amount)

    /**
     * Sets this member's xp.
     *
     * @see awardXp
     *
     * @param total the member's new xp
     * @return user's new xp
     */
    public suspend fun setXp(total: Int): Int =
        client.rest.server.setMemberXp(serverId, id, total)

    public suspend fun getMember(): Member =
        DeckMember.from(client, serverId, client.rest.server.getServerMember(serverId, id))
}