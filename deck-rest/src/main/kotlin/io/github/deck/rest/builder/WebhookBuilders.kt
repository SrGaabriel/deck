package io.github.deck.rest.builder

import io.github.deck.common.Embed
import io.github.deck.common.EmbedBuilder
import io.github.deck.common.util.nullableOptional
import io.github.deck.rest.request.CreateWebhookRequest
import io.github.deck.rest.request.ExecuteWebhookRequest
import io.github.deck.rest.request.UpdateWebhookRequest
import io.github.deck.rest.util.required
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import java.util.*

public class CreateWebhookRequestBuilder: RequestBuilder<CreateWebhookRequest> {
    public var name: String by required()
    public var channelId: UUID by required()

    override fun toRequest(): CreateWebhookRequest = CreateWebhookRequest(
        name = name,
        channelId = channelId
    )
}

public class UpdateWebhookRequestBuilder: RequestBuilder<UpdateWebhookRequest> {
    public var name: String by required()
    public var channelId: UUID? = null

    override fun toRequest(): UpdateWebhookRequest = UpdateWebhookRequest(
        name = name,
        channelId = channelId.nullableOptional()
    )
}

public class ExecuteWebhookRequestBuilder: RequestBuilder<ExecuteWebhookRequest> {
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

    public var username: String? = null
    public var avatar: String? = null

    public fun replyTo(vararg messageIds: UUID): Unit =
        repliesTo.addAll(messageIds).let {}

    public fun embed(builder: EmbedBuilder.() -> Unit) {
        embeds = embeds + EmbedBuilder().apply(builder).build()
    }

    override fun toRequest(): ExecuteWebhookRequest = ExecuteWebhookRequest(
        content = contentElement,
        embeds = embeds.map { it.toSerializable() },
        isPrivate = isPrivate,
        isSilent = silent,
        replyMessageIds = repliesTo,
        username = username,
        avatarUrl = avatar
    )
}