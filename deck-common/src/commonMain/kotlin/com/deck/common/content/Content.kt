package com.deck.common.content

import com.deck.common.content.node.Node
import com.deck.common.util.DeckDelicateApi
import com.deck.common.util.GuildedMedia

public class Content(public val nodes: List<Node> = listOf()) {
    public val leaves: List<String>
        get() = nodes.filterIsInstance<Node.Paragraph>().flatMap { it.data.children }.mapNotNull { it.data.text }
    public val images: List<String>
        get() = nodes.mapNotNull { it.data.image }
    public val embeds: List<Embed>
        get() = nodes.flatMap { it.data.embeds.orEmpty() }

    public val text: String
        get() = leaves.joinToString("\n")
}

public class ContentBuilder(private val quoteContainer: Boolean = false) {
    private val nodes: MutableList<Node> = mutableListOf()

    public operator fun Node.unaryPlus() {
        nodes.add(this)
    }

    public operator fun String.unaryPlus() {
        nodes.add(Node.Paragraph(listOf(Node.Paragraph.Text(this)), insideQuoteBlock = quoteContainer))
    }

    public operator fun GuildedMedia.unaryPlus() {
        nodes.add(Node.Image(image = this.url))
    }

    public operator fun Embed.unaryPlus() {
        nodes.add(Node.Embed(embeds = listOf(this)))
    }

    public operator fun Collection<Embed>.unaryPlus() {
        nodes.add(Node.Embed(embeds = this.toList()))
    }

    public fun paragraph(builder: ParagraphBuilder.() -> Unit): Node.Paragraph {
        return Node.Paragraph(
            ParagraphBuilder()
                .apply(builder)
                .nodes,
            insideQuoteBlock = quoteContainer
        )
    }

    @Suppress("unchecked_cast")
    public fun quote(builder: ContentBuilder.() -> Unit): Node.Quote {
        return Node.Quote(
            ContentBuilder(quoteContainer = true)
                .apply(builder)
                .nodes
        )
    }

    @DeckDelicateApi
    /**
     * This method only attaches the specified message if it's
     * from guilded's CDN, so to attach a separate image, you need to upload it via rest.
     */
    public fun image(url: String): Node.Image = Node.Image(image = url)

    public fun reaction(before: String = "", id: Int, after: String = ""): Node.Paragraph =
        Node.Paragraph(content = listOf(Node.Paragraph.Text(text = "$before "), Node.Paragraph.Reaction(id = id), Node.Paragraph.Text(text = " $after")), insideQuoteBlock = quoteContainer)

    public fun link(before: String = "", url: String, after: String = ""): Node.Paragraph =
        Node.Paragraph(content = listOf(Node.Paragraph.Text(text = "$before "), Node.Paragraph.Link(link = url), Node.Paragraph.Text(text = " $after")), insideQuoteBlock = quoteContainer)

    public operator fun invoke(builder: ContentBuilder.() -> Unit): Unit =
        this.let(builder)

    public fun build(): Content = Content(nodes)
}

public class ParagraphBuilder {
    public val nodes: MutableList<Node> = mutableListOf()

    public operator fun Node.unaryPlus() {
        nodes.add(this)
    }

    public operator fun List<Node>.unaryPlus() {
        nodes.addAll(this)
    }

    public operator fun String.unaryPlus() {
        nodes.add(Node.Paragraph.Text(this))
    }

    public fun link(url: String): Node.Paragraph.Link = Node.Paragraph.Link(link = url)

    public fun reaction(id: Int): Node.Paragraph.Reaction = Node.Paragraph.Reaction(id = id)

    @Deprecated("Why would you want to use this method when you're inside a paragraph?", replaceWith = ReplaceWith("reaction(id)"))
    public fun reaction(before: String = "", id: Int, after: String = ""): List<Node> =
        listOf(Node.Paragraph.Text(text = "$before "), Node.Paragraph.Reaction(id = id), Node.Paragraph.Text(text = " $after"))

    @Deprecated("Why would you want to use this method when you're inside a paragraph?", replaceWith = ReplaceWith("link(id)"))
    public fun link(before: String = "", url: String, after: String = ""): List<Node> =
        listOf(Node.Paragraph(content = listOf(Node.Paragraph.Text(text = "$before "), Node.Paragraph.Link(link = url), Node.Paragraph.Text(text = " $after"))))
}

public fun contentBuilder(builder: ContentBuilder.() -> Unit): Content =
    ContentBuilder().apply(builder).build()