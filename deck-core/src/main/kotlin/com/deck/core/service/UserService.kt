package com.deck.core.service

import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.core.entity.SelfUser
import com.deck.core.entity.User
import com.deck.core.entity.impl.DeckSelfUser
import com.deck.core.entity.impl.DeckUser
import com.deck.core.entity.misc.forcefullyWrap
import com.deck.rest.route.UserRoute

interface UserService {
    suspend fun getUser(id: GenericId): User?

    suspend fun getSelfUser(): SelfUser
}

class DefaultUserService(private val route: UserRoute): UserService {
    override suspend fun getUser(id: GenericId): User? = route.getUser(id)?.user?.let { target ->
        DeckUser(
            id = target.id,
            name = target.name,
            subdomain = target.subdomain.asNullable(),
            avatar = target.profilePicture.asNullable(),
            banner = target.profileBannerSm.asNullable(),
            aboutInfo = target.aboutInfo.asNullable().forcefullyWrap(),
            creationTime = target.joinDate.asNullable()!!,
            lastLoginTime = target.lastOnline.asNullable()!!,
        )
    }

    override suspend fun getSelfUser(): DeckSelfUser = route.getSelf().let { rawSelf ->
        return DeckSelfUser(
            route,
            id = rawSelf.user.id,
            name = rawSelf.user.name,
            subdomain = rawSelf.user.subdomain.asNullable(),
            avatar = rawSelf.user.profilePicture.asNullable(),
            banner = rawSelf.user.profileBannerLg.asNullable(),
            aboutInfo = rawSelf.user.aboutInfo.asNullable().forcefullyWrap(),
            creationTime = rawSelf.user.joinDate.asNullable()!!,
            lastLoginTime = rawSelf.user.lastOnline.asNullable()!!,
        )
    }
}
