package com.deck.common.entity

import com.deck.common.util.OptionalProperty
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
public data class RawEmbed(
    val title: OptionalProperty<String> = OptionalProperty.NotPresent,
    val description: OptionalProperty<String> = OptionalProperty.NotPresent,
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    val timestamp: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val color: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val footer: OptionalProperty<RawEmbedFooter> = OptionalProperty.NotPresent,
    val image: OptionalProperty<RawEmbedImage> = OptionalProperty.NotPresent,
    val thumbnail: OptionalProperty<RawEmbedImage> = OptionalProperty.NotPresent,
    val video: OptionalProperty<RawEmbedImage> = OptionalProperty.NotPresent,
    val provider: OptionalProperty<RawEmbedProvider> = OptionalProperty.NotPresent,
    val author: OptionalProperty<RawEmbedAuthor> = OptionalProperty.NotPresent,
    val fields: OptionalProperty<List<RawEmbedField>> = OptionalProperty.NotPresent
)

@Serializable
public data class RawEmbedAuthor(
    val name: OptionalProperty<String> = OptionalProperty.NotPresent,
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    val iconUrl: OptionalProperty<String> = OptionalProperty.NotPresent,
)

@Serializable
public data class RawEmbedProvider(
    val name: OptionalProperty<String> = OptionalProperty.NotPresent,
    val url: OptionalProperty<String> = OptionalProperty.NotPresent
)

@Serializable
public data class RawEmbedImage(
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    val height: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val width: OptionalProperty<Int> = OptionalProperty.NotPresent
)

@Serializable
public data class RawEmbedField(
    val name: String,
    val value: String,
    val inline: OptionalProperty<Boolean> = OptionalProperty.NotPresent
)

@Serializable
public data class RawEmbedFooter(
    val text: String,
    val iconUrl: OptionalProperty<String> = OptionalProperty.NotPresent
)
