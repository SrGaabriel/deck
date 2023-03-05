@file:UseSerializers(UUIDSerializer::class)

package io.github.srgaabriel.deck.common.entity

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.OptionalProperty
import io.github.srgaabriel.deck.common.util.UUIDSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
public data class RawServerChannel(
    public val id: UUID,
    public val type: ServerChannelType,
    public val name: String,
    public val topic: OptionalProperty<String> = OptionalProperty.NotPresent,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    public val serverId: GenericId,
    public val parentId: OptionalProperty<UUID> = OptionalProperty.NotPresent,
    public val categoryId: OptionalProperty<IntGenericId> = OptionalProperty.NotPresent,
    public val groupId: GenericId,
    public val isPublic: Boolean = false,
    public val archivedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val archivedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
)

@Serializable
public class RawChannelId(public val id: UUID)

@Serializable
public enum class ServerChannelType(public val contentName: String) {
    @SerialName("announcements")
    Announcements("announcements"),

    @SerialName("chat")
    Chat("content"),

    @SerialName("calendar")
    Calendar("events"),

    @SerialName("forums")
    Forums("topics"),

    @SerialName("media")
    Media("media"),

    @SerialName("docs")
    Documentation("docs"),

    @SerialName("voice")
    Voice("voice"),

    @SerialName("list")
    List("items"),

    @SerialName("scheduling")
    Scheduling("scheduling"),

    @SerialName("stream")
    Streaming("stream");
}

@Serializable
public data class RawDocumentation(
    public val id: IntGenericId,
    public val serverId: GenericId,
    public val channelId: UUID,
    public val title: String,
    public val content: String,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    public val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent
)

@Serializable
public data class RawDocumentationComment(
    public val id: IntGenericId,
    public val content: String,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    public val channelId: UUID,
    public val docId: IntGenericId,
    public val mentions: OptionalProperty<RawMessageMentions> = OptionalProperty.NotPresent
)

@Serializable
public data class RawListItem(
    public val id: UUID,
    public val serverId: GenericId,
    public val channelId: UUID,
    public val message: String,
    public val note: OptionalProperty<RawListItemNote> = OptionalProperty.NotPresent,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val createdByWebhookId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent
)

@Serializable
public data class RawListItemNote(
    public val content: OptionalProperty<String> = OptionalProperty.NotPresent,
    public val mentions: OptionalProperty<RawMessageMentions> = OptionalProperty.NotPresent,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    public val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
)

@Serializable
public data class RawListItemNoteSummary(
    public val content: OptionalProperty<String> = OptionalProperty.NotPresent,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    public val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
)

@Serializable
public data class RawForumTopic(
    val id: IntGenericId,
    val serverId: GenericId,
    val channelId: UUID,
    val title: String,
    val content: String,
    val createdAt: Instant,
    val createdBy: GenericId,
    val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val bumpedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val mentions: OptionalProperty<RawMessageMentions> = OptionalProperty.NotPresent
)

@Serializable
public data class RawForumTopicSummary(
    val id: IntGenericId,
    val serverId: GenericId,
    val channelId: UUID,
    val title: String,
    val createdAt: Instant,
    val createdBy: GenericId,
    val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val bumpedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent
)

@Serializable
public data class RawForumTopicComment(
    val id: IntGenericId,
    val content: String,
    val createdAt: Instant,
    val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val channelId: UUID,
    val forumTopicId: IntGenericId,
    val createdBy: GenericId,
    val mentions: OptionalProperty<RawMessageMentions> = OptionalProperty.NotPresent
)

@Serializable
public data class RawCalendarEvent(
    val id: IntGenericId,
    val serverId: GenericId,
    val channelId: UUID,
    val name: String,
    val description: OptionalProperty<String> = OptionalProperty.NotPresent,
    val location: OptionalProperty<String> = OptionalProperty.NotPresent,
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    val color: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val repeats: Boolean = false,
    val seriesId: OptionalProperty<UUID> = OptionalProperty.NotPresent,
    val roleIds: OptionalProperty<List<Int>> = OptionalProperty.NotPresent,
    val isAllDay: Boolean = false,
    val rsvpLimit: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val startsAt: Instant,
    val duration: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val isPrivate: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val mentions: OptionalProperty<RawMessageMentions> = OptionalProperty.NotPresent,
    val createdAt: Instant,
    val createdBy: GenericId,
    val cancellation: OptionalProperty<RawCalendarEventCancellation> = OptionalProperty.NotPresent
)

@Serializable
public data class RawCalendarEventCancellation(
    val description: OptionalProperty<String> = OptionalProperty.NotPresent,
    val createdBy: GenericId,
)

@Serializable
public data class RawCalendarEventRsvp(
    val calendarEventId: IntGenericId,
    val channelId: UUID,
    val serverId: GenericId,
    val userId: GenericId,
    val status: CalendarEventRsvpStatus,
    val createdBy: GenericId,
    val createdAt: Instant,
    val updatedBy: OptionalProperty<String> = OptionalProperty.NotPresent,
    val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent
)

@Serializable
public enum class CalendarEventRsvpStatus {
    @SerialName("going")
    Going,
    @SerialName("maybe")
    Maybe,
    @SerialName("declined")
    Declined,
    @SerialName("invited")
    Invited,
    @SerialName("waitlisted")
    Waitlisted;
}


@Serializable
public data class RawCalendarEventRepeatInfo(
    val type: RawCalendarEventRepeatInfoType,
    val every: OptionalProperty<RawCalendarEventRepeatInfoCustom> = OptionalProperty.NotPresent,
    val endAfterOccurrences: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val endDate: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val on: OptionalProperty<List<CalendarEventWeekDay>> = OptionalProperty.NotPresent
)

@Serializable
public data class RawCalendarEventRepeatInfoCustom(
    val count: Int,
    val interval: CalendarEventCustomIntervalType
)

@Serializable
public enum class RawCalendarEventRepeatInfoType {
    @SerialName("once")
    Once,
    @SerialName("everyDay")
    EveryDay,
    @SerialName("everyWeek")
    EveryWeek,
    @SerialName("everyMonth")
    EveryMonth,
    @SerialName("custom")
    Custom;
}

@Serializable
public enum class CalendarEventCustomIntervalType {
    @SerialName("once")
    Day,
    @SerialName("everyDay")
    Month,
    @SerialName("everyWeek")
    Year,
    @SerialName("everyMonth")
    Week,
}

@Serializable
public enum class CalendarEventWeekDay {
    @SerialName("sunday")
    Sunday,
    @SerialName("monday")
    Monday,
    @SerialName("tuesday")
    Tuesday,
    @SerialName("wednesday")
    Wednesday,
    @SerialName("thursday")
    Thursday,
    @SerialName("friday")
    Friday,
    @SerialName("saturday")
    Saturday
}

@Serializable
public data class RawCalendarEventComment(
    val id: IntGenericId,
    val content: String,
    val createdAt: Instant,
    val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val calendarEventId: IntGenericId,
    val channelId: UUID,
    val createdBy: GenericId,
    val mentions: OptionalProperty<RawMessageMentions> = OptionalProperty.NotPresent
)