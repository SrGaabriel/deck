package com.deck.common

import com.deck.common.content.ContentBuilder
import com.deck.common.content.EmbedBuilder

public fun ContentBuilder.embed(builder: EmbedBuilder.() -> Unit): Unit =
    + EmbedBuilder().apply(builder).build()
