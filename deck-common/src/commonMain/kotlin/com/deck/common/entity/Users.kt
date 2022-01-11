package com.deck.common.entity

import com.deck.common.util.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RawUser(
    val id: GenericId,
    val name: String,
    val subdomain: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val email: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val profilePicture: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val profilePictureSm: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val profilePictureLg: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val profilePictureBlur: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val profileBannerSm: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val profileBannerLg: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val profileBannerBlur: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val joinDate: OptionalProperty<Timestamp> = OptionalProperty.NotPresent,
    val steamId: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val userStatus: RawUserStatus,
    val userPresenceStatus: RawUserPresenceStatus,
    val userTransientStatus: OptionalProperty<RawTransientStatus?> = OptionalProperty.NotPresent,
    @DeckUnknown
    val moderationStatus: Unit? = null,
    val aboutInfo: OptionalProperty<RawUserAboutInfo?> = OptionalProperty.NotPresent,
    val lastOnline: OptionalProperty<Timestamp> = OptionalProperty.NotPresent,
    val stonks: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val flairInfos: OptionalProperty<List<RawUserFlair>> = OptionalProperty.NotPresent
)

@Serializable
data class RawUserStatus(
    @DeckUnsupported
    val content: String? = null,
    val customReactionId: OptionalProperty<IntGenericId?> = OptionalProperty.NotPresent,
    @DeckUnknown
    val customReaction: OptionalProperty<RawEmoji?> = OptionalProperty.NotPresent,
    val expireInMs: Long
)

@Serializable
data class RawTransientStatus(
    val id : IntGenericId,
    @SerialName("gameId") val game: GameStatus,
    val type: PresenceType,
    val startedAt: Timestamp,
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent
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

@Serializable
data class RawFriend(
    val friendUserId: GenericId,
    val friendStatus: String?,
    val createdAt: Timestamp
)

@Serializable
data class RawUserPost(
    val id: IntGenericId,
    val title: String,
    val message: RawMessageContent,
    val createdAt: Timestamp,
    val bumpedAt: Timestamp,
    val editedAt: Timestamp?,
    val isShare: Boolean,
    val createdBy: GenericId,
    val userId: GenericId,
    val publishUrl: String?,
    val publishedAt: Timestamp?,
    val createdByInfo: RawPostCreatedByInfo
)

@Serializable
data class RawPostCreatedByInfo(
    val id: GenericId,
    val name: String,
    val profilePicture: String?,
    val profileBannerBlur: String?
)

@Serializable(RawUserPresenceStatus.Serializer::class)
enum class RawUserPresenceStatus(val id: Int) {
    Online(1), Idle(2), Busy(3), Offline(4);

    companion object Serializer: IntIdEnumSerializer<RawUserPresenceStatus>(
        IntSerializationStrategy(values().associateBy { it.id })
    )
}