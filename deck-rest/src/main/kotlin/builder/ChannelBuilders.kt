package io.github.srgaabriel.deck.rest.builder

import io.github.srgaabriel.deck.common.Embed
import io.github.srgaabriel.deck.common.EmbedBuilder
import io.github.srgaabriel.deck.common.entity.*
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.nullableOptional
import io.github.srgaabriel.deck.common.util.optional
import io.github.srgaabriel.deck.rest.request.*
import io.github.srgaabriel.deck.rest.util.required
import kotlinx.datetime.Instant
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

public class CreateChannelRequestBuilder: RequestBuilder<CreateChannelRequest> {
    public var name: String by required()
    public var topic: String? = null

    public var type: ServerChannelType = ServerChannelType.Chat
    public var isPublic: Boolean = false

    // You must provide at least one of these
    public var serverId: GenericId? = null
    public var groupId: GenericId? = null
    public var categoryId: IntGenericId? = null

    override fun toRequest(): CreateChannelRequest = CreateChannelRequest(
        name = name.optional(),
        topic = topic.nullableOptional(),
        isPublic = isPublic,
        type = type.optional(),
        serverId = serverId.nullableOptional(),
        groupId = groupId.nullableOptional(),
        categoryId = categoryId.nullableOptional()
    )
}

public class SendMessageRequestBuilder: RequestBuilder<SendMessageRequest> {
    public var isPrivate: Boolean = false
    public val repliesTo: MutableList<UUID> = mutableListOf()

    public var content: String?
        set(value) {
            contentElement = if (value == null) null else JsonPrimitive(value)
        }
        get() = contentElement?.jsonPrimitive?.content
    public var contentElement: JsonElement? = null

    public var embeds: List<Embed> = listOf()
    public val silent: Boolean = false

    public fun replyTo(vararg messageIds: UUID): Unit =
        repliesTo.addAll(messageIds).let {}

    public fun embed(builder: EmbedBuilder.() -> Unit) {
        embeds = embeds + EmbedBuilder().apply(builder).build()
    }

    override fun toRequest(): SendMessageRequest = SendMessageRequest(
        content = contentElement,
        embeds = embeds.map { it.toSerializable() },
        isPrivate = isPrivate,
        isSilent = silent,
        replyMessageIds = repliesTo
    )
}

public class UpdateMessageRequestBuilder: RequestBuilder<UpdateMessageRequest> {
    public var content: String?
        set(value) {
            contentElement = if (value == null) null else JsonPrimitive(value)
        }
        get() = contentElement?.jsonPrimitive?.content
    public var contentElement: JsonElement? = null
    public var embeds: List<Embed> = listOf()

    public fun embed(builder: EmbedBuilder.() -> Unit) {
        embeds = embeds + EmbedBuilder().apply(builder).build()
    }

    override fun toRequest(): UpdateMessageRequest = UpdateMessageRequest(
        content = contentElement,
        embeds = embeds.map { it.toSerializable() },
    )
}

public class CreateDocumentationRequestBuilder: RequestBuilder<CreateDocumentationRequest> {
    public var title: String by required()
    public var content: String by required()

    override fun toRequest(): CreateDocumentationRequest = CreateDocumentationRequest(
        title = title,
        content = content
    )
}

public class CreateListItemRequestBuilder: RequestBuilder<CreateListItemRequest> {
    public var label: String by required()
    public var note: String? = null

    override fun toRequest(): CreateListItemRequest = CreateListItemRequest(
        message = label,
        note = note?.let { JsonObject(mapOf("content" to JsonPrimitive(note))) }.nullableOptional()
    )
}

public typealias UpdateListItemRequestBuilder = CreateListItemRequestBuilder

public class CreateForumTopicRequestBuilder: RequestBuilder<CreateForumTopicRequest> {
    public var title: String by required()
    public var content: String by required()

    override fun toRequest(): CreateForumTopicRequest = CreateForumTopicRequest(
        title = title,
        content = content
    )
}

public class UpdateForumTopicRequestBuilder: RequestBuilder<UpdateForumTopicRequest> {
    public var title: String? = null
    public var content: String? = null

    override fun toRequest(): UpdateForumTopicRequest = UpdateForumTopicRequest(
        title = title.nullableOptional(),
        content = content.nullableOptional()
    )
}

public class UpdateCalendarEventRequestBuilder(): RequestBuilder<UpdateCalendarEventRequest> {
    public var name: String? = null
    public var description: String? = null
    public var location: String? = null
    public var url: String? = null

    public var startsAt: Instant? = null

    public var color: Int? = null
    public var durationInMinutes: Int? = null

    public var duration: Duration?
        get() = durationInMinutes?.minutes
        set(value) { durationInMinutes = value?.inWholeMinutes?.coerceAtMost(Int.MAX_VALUE.toLong() - 1)?.toInt() }

    public var isPrivate: Boolean = false

    public var isAllDay: Boolean? = null
    public var rsvpLimit: Int? = null

    public var roleIds: List<IntGenericId>? = null
    public var repeatInfo: Repeat? = null

    public sealed interface Repeat {
        public sealed class Fixed(internal val type: RawCalendarEventRepeatInfoType): Repeat {
            public object Once: Fixed(RawCalendarEventRepeatInfoType.Once)
            public object EveryDay: Fixed(RawCalendarEventRepeatInfoType.EveryDay)
            public object EveryWeek: Fixed(RawCalendarEventRepeatInfoType.EveryWeek)
            public object EveryMonth: Fixed(RawCalendarEventRepeatInfoType.EveryMonth)

            override fun toSerializable(): RawCalendarEventRepeatInfo =
                RawCalendarEventRepeatInfo(type = type)
        }

        public sealed class Custom(
            internal val type: CalendarEventCustomIntervalType,
            private val count: Int,
            private val endsAfterOccurrences: Int?,
            private val endDate: Instant?,
            private val on: List<CalendarEventWeekDay>?
        ): Repeat {
            public class Days(count: Int, endsAfterOccurrences: Int?, endDate: Instant?  = null): Custom(
                type = CalendarEventCustomIntervalType.Day,
                count = count,
                endsAfterOccurrences = endsAfterOccurrences,
                endDate = endDate,
                on = null,
            )

            public class Week(count: Int, endsAfterOccurrences: Int? = null, endDate: Instant? = null, on: List<CalendarEventWeekDay>?  = null): Custom(
                type = CalendarEventCustomIntervalType.Week,
                count = count,
                endsAfterOccurrences = endsAfterOccurrences,
                endDate = endDate,
                on = on
            )

            public class Month(count: Int, endsAfterOccurrences: Int? = null, endDate: Instant? = null): Custom(
                type = CalendarEventCustomIntervalType.Month,
                count = count,
                endsAfterOccurrences = endsAfterOccurrences,
                endDate = endDate,
                on = null,
            )

            public class Year(count: Int, endsAfterOccurrences: Int? = null, endDate: Instant? = null): Custom(
                type = CalendarEventCustomIntervalType.Year,
                count = count,
                endsAfterOccurrences = endsAfterOccurrences,
                endDate = endDate,
                on = null,
            )

            override fun toSerializable(): RawCalendarEventRepeatInfo =
                RawCalendarEventRepeatInfo(
                    type = RawCalendarEventRepeatInfoType.Custom,
                    every = RawCalendarEventRepeatInfoCustom(
                        count = count,
                        interval = type
                    ).optional(),
                    endAfterOccurrences = endsAfterOccurrences.nullableOptional(),
                    endDate = endDate.nullableOptional(),
                    on = on.nullableOptional()
                )
        }

        public fun toSerializable(): RawCalendarEventRepeatInfo
    }

    override fun toRequest(): UpdateCalendarEventRequest = UpdateCalendarEventRequest(
        name = name.nullableOptional(),
        description = description.nullableOptional(),
        location = location.nullableOptional(),
        startsAt = startsAt.nullableOptional(),
        url = url.nullableOptional(),
        color = color.nullableOptional(),
        isAllDay = isAllDay.nullableOptional(),
        rsvpLimit = rsvpLimit.nullableOptional(),
        duration = durationInMinutes.nullableOptional(),
        roleIds = roleIds.nullableOptional(),
        isPrivate = isPrivate,
        repeatInfo = repeatInfo?.toSerializable().nullableOptional()
    )
}