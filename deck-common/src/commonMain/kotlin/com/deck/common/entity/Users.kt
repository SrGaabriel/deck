package com.deck.common.entity

import com.deck.common.util.*
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class RawUser(
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
    val joinDate: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val steamId: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val userStatus: RawUserStatus,
    val userPresenceStatus: OptionalProperty<RawUserPresenceStatus> = OptionalProperty.NotPresent,
    val userTransientStatus: OptionalProperty<RawTransientStatus?> = OptionalProperty.NotPresent,
    @DeckUnknown
    val moderationStatus: Unit? = null,
    val aboutInfo: OptionalProperty<RawUserAboutInfo?> = OptionalProperty.NotPresent,
    val lastOnline: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val stonks: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val flairInfos: OptionalProperty<List<RawUserFlair>> = OptionalProperty.NotPresent
)

@Serializable
public data class RawSelfUser(
    val updateMessage: String?,
    val user: RawUser,
    val teams: List<RawTeam>,
    val customReactions: List<RawCustomReaction>,
    val customEmojis: OptionalProperty<List<GenericId>> = OptionalProperty.NotPresent,
    val landingUrl: Boolean,
    val friends: List<RawFriend>
)

@Serializable
public data class RawUserStatus(
    val content: RawMessageContent?,
    val customReactionId: OptionalProperty<IntGenericId?> = OptionalProperty.NotPresent,
    @DeckUnknown
    val customReaction: OptionalProperty<RawCustomReaction?> = OptionalProperty.NotPresent,
    val expireInMs: OptionalProperty<Long> = OptionalProperty.NotPresent
)

@Serializable
public data class RawTransientStatus(
    val id: IntGenericId,
    @SerialName("gameId") val game: Int,
    val type: RawUserPresenceType,
    val startedAt: Instant,
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent
)

@Serializable
public data class RawUserAboutInfo(
    val bio: String?,
    val tagLine: OptionalProperty<String> = OptionalProperty.NotPresent
)

@Serializable
public data class RawUserFlair(
    val flair: String,
    val amount: Int
)

@Serializable
public data class RawFriend(
    val friendUserId: GenericId,
    val friendStatus: String?,
    val createdAt: Instant
)

/** @param channelId is missing when listing user permissions in a category */
@Serializable
public data class RawUserPermission(
    val userId: GenericId,
    val channelId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val denyPermissions: RawRolePermissions,
    val allowPermissions: RawRolePermissions
)

@Serializable
public data class RawUserPost(
    val id: IntGenericId,
    val title: String,
    val message: RawMessageContent,
    val createdAt: Instant,
    val bumpedAt: Instant,
    val editedAt: Instant?,
    val isShare: Boolean,
    val createdBy: GenericId,
    val userId: GenericId,
    val publishUrl: String?,
    val publishedAt: Instant?,
    val createdByInfo: RawPostCreatedByInfo
)

@Serializable
public data class RawPostCreatedByInfo(
    val id: GenericId,
    val name: String,
    val profilePicture: String?,
    val profileBannerBlur: String?
)

@Serializable
public data class RawGenericIdObject(
    val id: GenericId
)

@Serializable(RawUserPresenceStatus.Serializer::class)
public enum class RawUserPresenceStatus(public val id: Int) {
    Online(1), Idle(2), Busy(3), Offline(4);

    public companion object Serializer : IntIdEnumSerializer<RawUserPresenceStatus>(
        IntSerializationStrategy(values().associateBy { it.id })
    )
}