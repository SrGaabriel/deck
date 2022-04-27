package io.github.deck.rest.builder

import io.github.deck.common.Embed
import io.github.deck.common.EmbedBuilder
import io.github.deck.common.util.mapToModel
import io.github.deck.common.util.nullableOptional
import io.github.deck.rest.request.*
import io.github.deck.rest.util.required
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import java.util.*

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
        replyMessageIds = repliesTo.toList().map(UUID::mapToModel)
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
        note = note.nullableOptional()
    )
}

public typealias UpdateListItemRequestBuilder = CreateListItemRequestBuilder

public class CreateForumThreadRequestBuilder: RequestBuilder<CreateForumThreadRequest> {
    public var title: String by required()
    public var content: String by required()

    override fun toRequest(): CreateForumThreadRequest = CreateForumThreadRequest(
        title = title,
        content = content
    )
}