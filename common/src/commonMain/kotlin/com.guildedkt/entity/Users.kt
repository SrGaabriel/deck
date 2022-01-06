package com.guildedkt.entity

import com.guildedkt.util.*
import kotlinx.serialization.Serializable

@Serializable
data class RawUser(
    val id: GenericId,
    val name: String,
    val subdomain: String?,
    val email: String?,
    val profilePicture: String,
    val profilePictureSm: String,
    val profilePictureLg: String,
    val profilePictureBlur: String,
    val profileBannerSm: String,
    val profileBannerLg: String,
    val profileBannerBlur: String,
    val joinDate: Timestamp,
    val steamId: String?,
    val userStatus: RawUserStatus,
    val userPresenceStatus: RawUserPresenceStatus,
    val userTransientStatus: TransientStatus,
    @GuildedUnknown
    val moderationStatus: Unit? = null,
    val aboutInfo: RawUserAboutInfo,
    val lastOnline: Timestamp,
    val stonks: Int?,
    val flairInfos: List<RawUserFlair>
)

@Serializable
data class RawUserStatus(
    @LibraryUnsupported
    val content: String? = null,
    val customReactionId: IntGenericId?,
    val customReaction: RawEmoji
)

@Serializable
data class RawUserAboutInfo(
    val bio: String?,
    val tagLine: String?
)

@Serializable
data class RawUserFlair(
    val flair: String,
    val amount: Int
)

@Serializable(RawUserPresenceStatus.Serializer::class)
enum class RawUserPresenceStatus(val id: Int) {
    Online(1), Idle(2), Busy(3), Offline(4);

    companion object Serializer: IntIdEnumSerializer<RawUserPresenceStatus>(
        IntSerializationStrategy(values().associateBy { it.id })
    )
}
