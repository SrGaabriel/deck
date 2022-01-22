package com.deck.common.content

import com.deck.common.content.node.Node

public class Content(public val nodes: List<Node> = listOf()) {
    public val leaves: List<String>
        get() = nodes.filterIsInstance<Node.Text>().mapNotNull { it.data.text }
    public val images: List<String>
        get() = nodes.filterIsInstance<Node.Image>().mapNotNull { it.data.image }
    public val embeds: List<Embed>
        get() = nodes.filterIsInstance<Node.Embed>().flatMap { it.data.embeds.orEmpty() }

    public val text: String
        get() = leaves.joinToString("\n")
}

public class ContentBuilder {
    private val nodes: MutableList<Node> = mutableListOf()

    public operator fun Node.unaryPlus() {
        nodes.add(this)
    }

    public operator fun String.unaryPlus() {
        nodes.add(Node.Text(text = this))
    }

    public operator fun Image.unaryPlus() {
        nodes.add(Node.Image(image = this.url))
    }

    public operator fun Embed.unaryPlus() {
        nodes.add(Node.Embed(embeds = listOf(this)))
    }

    public operator fun Collection<Embed>.unaryPlus() {
        nodes.add(Node.Embed(embeds = this.toList()))
    }

    public fun image(url: String): Image = Image(url)

    public operator fun invoke(builder: ContentBuilder.() -> Unit): Unit =
        this.let(builder)

    public fun build(): Content = Content(nodes)

    public data class Image(
        public val url: String
    )
}

public fun contentBuilder(builder: ContentBuilder.() -> Unit): Content =
    ContentBuilder().apply(builder).build()
