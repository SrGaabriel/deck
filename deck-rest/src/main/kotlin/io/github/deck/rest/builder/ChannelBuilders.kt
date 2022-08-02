package io.github.deck.rest.builder

import io.github.deck.common.Embed
import io.github.deck.common.EmbedBuilder
import io.github.deck.common.entity.RawEmbed
import io.github.deck.common.entity.RawServerChannelType
import io.github.deck.common.util.*
import io.github.deck.rest.request.*
import io.github.deck.rest.util.required
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
    public var type: RawServerChannelType by required()

    public var isPublic: Boolean = false
    // You must choose at least one of those to provide
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
        embeds = embeds.map { formatAndSerializeEmbed(it) },
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
        embeds = embeds.map { formatAndSerializeEmbed(it) },
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

public class CreateCalendarEventRequestBuilder: RequestBuilder<CreateCalendarEventRequest> {
    public var name: String by required()
    public var durationInMinutes: Int = 60
    public var duration: Duration
        get() = durationInMinutes.minutes
        set(value) { durationInMinutes = value.inWholeMinutes.coerceAtMost(Int.MAX_VALUE.toLong() - 1).toInt() }

    public var description: String? = null

    public var location: String? = null
    public var url: String? = null
    public var startsAt: Instant? = null

    public var color: Int? = null

    public var isPrivate: Boolean = false

    override fun toRequest(): CreateCalendarEventRequest = CreateCalendarEventRequest(
        name = name.optional(),
        description = description.nullableOptional(),
        location = location.nullableOptional(),
        startsAt = startsAt.nullableOptional(),
        url = url.nullableOptional(),
        color = color.nullableOptional(),
        duration = duration.inWholeMinutes.toInt(),
        isPrivate = isPrivate
    )

}

public class UpdateCalendarEventRequestBuilder: RequestBuilder<UpdateCalendarEventRequest> {
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

    override fun toRequest(): UpdateCalendarEventRequest = UpdateCalendarEventRequest(
        name = name.nullableOptional(),
        description = description.nullableOptional(),
        location = location.nullableOptional(),
        startsAt = startsAt.nullableOptional(),
        url = url.nullableOptional(),
        color = color.nullableOptional(),
        duration = durationInMinutes.nullableOptional(),
        isPrivate = isPrivate
    )

}

private fun formatAndSerializeEmbed(embed: Embed): RawEmbed {
    val serialized = embed.toSerializable()
    val fieldList = serialized.fields.asNullable()
    return when {
        fieldList == null -> serialized
        fieldList.isEmpty() -> serialized.copy(fields = OptionalProperty.NotPresent)
        else -> serialized
    }
}
