package com.deck.core.delegator

import com.deck.common.entity.*
import com.deck.common.util.GenericId
import com.deck.core.entity.*
import com.deck.core.entity.channel.*
import com.deck.gateway.entity.RawPartialTeamChannel
import com.deck.rest.entity.RawChannelForumThreadReply
import com.deck.rest.entity.RawFetchedMember
import com.deck.rest.entity.RawFetchedTeam
import java.util.*

public interface EntityDecoder {
    public fun decodeTeam(raw: RawFetchedTeam): Team

    public fun decodeUser(raw: RawUser): User

    public fun decodeSelf(raw: RawSelfUser): SelfUser

    public fun decodeChannel(raw: RawChannel): Channel

    public fun decodeRole(raw: RawRole): Role

    public fun decodeGroup(raw: RawGroup): Group

    public fun decodeMember(teamId: GenericId, raw: RawFetchedMember): Member

    public fun decodeUserPermissionsOverride(teamId: GenericId, raw: RawUserPermission): UserPermissionsOverride

    public fun decodeRolePermissions(raw: RawRolePermissions): RolePermissions

    public fun decodeRolePermissionsOverride(raw: RawRolePermissionsOverride): RolePermissionsOverride

    public fun decodePartialSentMessage(channelId: UUID, teamId: GenericId?, raw: RawPartialSentMessage): Message

    public fun decodePartialTeamChannel(teamId: GenericId, raw: RawPartialTeamChannel): PartialTeamChannel

    public fun decodeForumThread(raw: RawChannelForumThread): ForumThread

    public fun decodeForumThreadReply(channelId: UUID, raw: RawChannelForumThreadReply): ForumPost

    public fun decodeScheduleAvailability(raw: RawChannelAvailability): ScheduleAvailability
}