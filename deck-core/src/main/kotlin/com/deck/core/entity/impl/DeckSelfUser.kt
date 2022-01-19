package com.deck.core.entity.impl

import com.deck.common.entity.RawUser
import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.common.util.asNullable
import com.deck.core.DeckClient
import com.deck.core.entity.SelfUser
import com.deck.core.entity.misc.DeckUserAboutInfo
import com.deck.core.entity.misc.forcefullyWrap

public class DeckSelfUser(
    override val client: DeckClient,
    public val raw: RawUser
) : SelfUser {
    override val id: GenericId get() = raw.id

    override var name: String = raw.name

    override var subdomain: String? = raw.subdomain.asNullable()

    override var avatar: String? = raw.profilePicture.asNullable()

    override var banner: String? = raw.profileBannerSm.asNullable()

    override var aboutInfo: DeckUserAboutInfo? = raw.aboutInfo.asNullable().forcefullyWrap()

    override val creationTime: Timestamp get() = raw.joinDate.asNullable()!!

    override val lastLoginTime: Timestamp? get() = raw.lastOnline.asNullable()
}
