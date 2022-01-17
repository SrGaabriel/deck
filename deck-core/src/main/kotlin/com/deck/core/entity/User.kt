package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.core.builder.DeckModifySelfBuilder
import com.deck.core.entity.misc.DeckUserAboutInfo

interface User {
    val id: GenericId
    val name: String
    val subdomain: String?

    /** Null when user doesn't have an specific avatar set (default doggy avatar) */
    val avatar: String?
    /** Null when user doesn't have a banner set (empty) */
    val banner: String?

    val aboutInfo: DeckUserAboutInfo?

    val creationTime: Timestamp
    val lastLoginTime: Timestamp
}

interface SelfUser: User {
    suspend fun editProfile(editor: DeckModifySelfBuilder.() -> Unit)

    suspend fun setName(name: String)

    suspend fun setAvatar(url: String)

    suspend fun setBanner(url: String)

    suspend fun setSubdomain(subdomain: String)

    suspend fun setAboutInfo(aboutInfo: DeckUserAboutInfo)
}
