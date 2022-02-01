package com.deck.core.delegator

import com.deck.common.entity.*
import com.deck.common.util.GenericId
import com.deck.core.entity.*
import com.deck.core.entity.channel.Channel
import com.deck.core.entity.channel.ForumThread
import com.deck.core.entity.channel.PartialTeamChannel
import com.deck.gateway.entity.RawPartialTeamChannel
import com.deck.rest.entity.RawFetchedTeam
import java.util.*

public interface EntityDecoder {
    public fun decodeTeam(raw: RawFetchedTeam): Team

    public fun decodeUser(raw: RawUser): User

    public fun decodeSelf(raw: RawSelfUser): SelfUser

    public fun decodeChannel(raw: RawChannel): Channel

    public fun decodeRole(raw: RawRole): Role

    public fun decodeUserPermissionsOverride(teamId: GenericId, raw: RawUserPermission): UserPermissionsOverride

    public fun decodeRolePermissions(raw: RawRolePermissions): RolePermissions

    public fun decodeRolePermissionsOverride(raw: RawRolePermissionsOverride): RolePermissionsOverride

    public fun decodePartialSentMessage(channelId: UUID, teamId: GenericId?, raw: RawPartialSentMessage): Message

    public fun decodePartialTeamChannel(teamId: GenericId, raw: RawPartialTeamChannel): PartialTeamChannel

    public fun decodeForumThread(raw: RawChannelForumThread): ForumThread
}
