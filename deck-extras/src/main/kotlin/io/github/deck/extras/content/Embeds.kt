package io.github.deck.extras.content

import io.github.deck.common.util.asNullable
import io.github.deck.common.util.map
import io.github.deck.common.util.nullableOptional
import io.github.deck.common.util.optional
import kotlinx.datetime.Instant

private const val INLINE_BY_DEFAULT = false

public data class Embed(
    public val author: Author? = null,
    public val title: String? = null,
    public val description: String? = null,
    public val url: String? = null,
    public val color: Int? = null,
    public val fields: List<Field> = emptyList(),
    public val thumbnail: String? = null,
    public val image: String? = null,
    public val footer: Footer? = null,
    public val timestamp: Instant? = null,
) {
    public data class Author(
        public val title: String? = null,
        public val url: String? = null,
        public val iconUrl: String? = null,
    )

    public data class Field(
        public val name: String,
        public val value: String,
        public val inline: Boolean = INLINE_BY_DEFAULT
    )

    public data class Footer(
        public val text: String,
        public val iconUrl: String? = null,
    )

    public fun toSerializable(): RawEmbed = RawEmbed(
        title = title.nullableOptional(),
        description = description.nullableOptional(),
        url = url.nullableOptional(),
        timestamp = timestamp.nullableOptional(),
        color = color.nullableOptional(),
        footer = footer.nullableOptional().map { RawEmbedFooter(it.text, it.iconUrl.nullableOptional()) },
        image = image.nullableOptional().map { RawEmbedImage(url = it.optional()) },
        thumbnail = thumbnail.nullableOptional().map { RawEmbedImage(url = it.optional()) },
        author = author.nullableOptional().map { RawEmbedAuthor(it.title.nullableOptional(), it.url.nullableOptional(), it.iconUrl.nullableOptional()) },
        fields = fields.nullableOptional().map { it.map { RawEmbedField(it.name, it.value, it.inline.optional()) } }
    )

    public companion object
}

public class EmbedBuilder {
    public var title: String? = null
    public var description: String? = null

    public var color: Int? = null
    public var timestamp: Instant? = null

    public var image: String? = null
    public var thumbnail: String? = null

    public var url: String? = null

    private var author: Embed.Author? = null
    private var fields: MutableList<Embed.Field> = mutableListOf()
    private var footer: Embed.Footer? = null

    public class Author {
        public var text: String? = null
        public var url: String? = null
        public var iconUrl: String? = null
    }

    public class Field {
        public var name: String? = null
        public var value: String? = null
        public var inline: Boolean = INLINE_BY_DEFAULT
    }

    public class Footer {
        public var text: String? = null
        public var iconUrl: String? = null
    }

    public fun author(scope: Author.() -> Unit): Unit = Author().apply(scope).let {
        this.author = Embed.Author(
            it.text,
            it.url,
            it.iconUrl
        )
    }

    public fun field(scope: Field.() -> Unit): Unit = Field().apply(scope).let {
        field(
            it.name ?: error("Can't attribute an empty name to an embed field."),
            it.value?: error("Can't attribute an empty value to an embed field."),
            it.inline
        )
    }

    public fun field(name: String, value: String, inline: Boolean = INLINE_BY_DEFAULT): Unit =
        this.fields.add(Embed.Field(name, value, inline)).let {}

    public fun footer(scope: Footer.() -> Unit): Unit = Footer().apply(scope).let {
        this.footer = Embed.Footer(
            it.text ?: error("Can't attribute an empty text to an embed footer."),
            it.iconUrl
        )
    }

    public fun build(): Embed = Embed(
        url = url,
        title = title,
        description = description,
        author = author,
        fields = fields,
        footer = footer,
        image = image,
        thumbnail = thumbnail,
        color = color,
        timestamp = timestamp
    )
}

public fun Embed.Companion.from(raw: RawEmbed): Embed = Embed(
    title = raw.title.asNullable(),
    description = raw.description.asNullable(),
    url = raw.url.asNullable(),
    timestamp = raw.timestamp.asNullable(),
    color = raw.color.asNullable(),
    footer = raw.footer.asNullable()?.let { Embed.Footer(it.text, it.iconUrl.asNullable()) },
    image = raw.image.asNullable()?.url?.asNullable(),
    thumbnail = raw.thumbnail.asNullable()?.url?.asNullable(),
    author = raw.author.asNullable()?.let { Embed.Author(it.name.asNullable(), it.url.asNullable(), it.iconUrl.asNullable()) },
    fields = raw.fields.asNullable()?.map { Embed.Field(it.name, it.value, it.inline.asNullable() == true) }.orEmpty()
)