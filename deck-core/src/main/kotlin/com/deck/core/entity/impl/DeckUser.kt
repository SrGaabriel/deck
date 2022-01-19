package com.deck.core.entity.impl

import com.deck.common.entity.RawUser
import com.deck.common.entity.RawUserPermission
import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.RolePermissions
import com.deck.core.entity.User
import com.deck.core.entity.UserPermission
import com.deck.core.entity.misc.DeckUserAboutInfo
import com.deck.core.entity.misc.forcefullyWrap
import java.util.*

public data class DeckUser(
    override val client: DeckClient,
    public val raw: RawUser
) : User {
    override val id: GenericId get() = raw.id

    override val name: String get() = raw.name

    override val subdomain: String? get() = raw.subdomain.asNullable()

    override val avatar: String? get() = raw.profilePicture.asNullable()

    override val banner: String? get() = raw.profileBannerSm.asNullable()

    override val aboutInfo: DeckUserAboutInfo get() = raw.aboutInfo.asNullable().forcefullyWrap()

    override val creationTime: Timestamp get() = raw.joinDate.asNullable()!!

    override val lastLoginTime: Timestamp? get() = raw.lastOnline.asNullable()
}

public data class DeckUserPermission(
    override val client: DeckClient,
    public val raw: RawUserPermission
) : UserPermission {
    override val userId: GenericId get() = raw.userId

    override val channelId: UUID? get() = raw.channelId.asNullable()?.mapToBuiltin()

    override val createdAt: Timestamp get() = raw.createdAt

    override val updatedAt: Timestamp? get() = raw.updatedAt

    override val denyPermissions: RolePermissions get() = DeckRolePermissions(raw.denyPermissions)

    override val allowPermissions: RolePermissions get() = DeckRolePermissions(raw.denyPermissions)
}