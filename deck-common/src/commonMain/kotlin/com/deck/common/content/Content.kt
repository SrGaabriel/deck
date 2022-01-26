package com.deck.common.content

import com.deck.common.content.node.Node
import com.deck.common.entity.RawMessageContentNodeLeavesMarkType
import com.deck.common.util.DeckDelicateApi
import com.deck.common.util.GuildedMedia

public class Content(public val nodes: List<Node> = emptyList()) {
    public val leaves: List<Node.Paragraph.Text.Leaf>
        get() = nodes.filterIsInstance<Node.Paragraph>().flatMap { it.data.children }.filter { it.data.leaves != null }.flatMap { it.data.leaves!! }
    public val texts: List<String>
        get() = leaves.map { it.text }
    public val images: List<String>
        get() = nodes.filterIsInstance<Node.Image>().map { it.image }
    public val embeds: List<Embed>
        get() = nodes.filterIsInstance<Node.Embed>().flatMap { it.embeds }

    public val text: String
        get() = texts.joinToString("\n")
}

public class ContentBuilder(private val quoteContainer: Boolean = false): Markable {
    private val nodes: MutableList<Node> = mutableListOf()

    public operator fun Node.unaryPlus() {
        nodes.add(this)
    }

    override fun Node.Paragraph.Text.Leaf.unaryPlus() {
        nodes.add(Node.Paragraph(content = listOf(Node.Paragraph.Text(listOf(this)))))
    }

    public operator fun String.unaryPlus() {
        nodes.add(text { + this@unaryPlus })
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

    public fun quote(builder: ContentBuilder.() -> Unit): Node.Quote {
        return Node.Quote(
            ContentBuilder(quoteContainer = true)
                .apply(builder)
                .nodes
        )
    }

    public fun text(text: String, marks: List<RawMessageContentNodeLeavesMarkType> = emptyList()): Node.Paragraph.Text =
        Node.Paragraph.Text(leaves = listOf(Node.Paragraph.Text.Leaf(text, marks)))

    public fun text(builder: LeavesBuilder.() -> Unit): Node.Paragraph = paragraph {
        + Node.Paragraph.Text(
            LeavesBuilder()
                .apply(builder)
                .leaves
        )
    }

    @DeckDelicateApi
    /**
     * This method only attaches the specified message if it's
     * from guilded's CDN, so to attach a separate image, you need to upload it via rest.
     */
    public fun image(url: String): Node.Image = Node.Image(image = url)

    public fun reaction(before: String = "", id: Int, after: String = ""): Node.Paragraph =
        Node.Paragraph(content = listOf(text(before), Node.Paragraph.Reaction(id = id), text(after)), insideQuoteBlock = quoteContainer)

    public fun link(before: String = "", url: String, after: String = ""): Node.Paragraph =
        Node.Paragraph(content = listOf(text(before), Node.Paragraph.Link(link = url), text(after)), insideQuoteBlock = quoteContainer)

    public operator fun invoke(builder: ContentBuilder.() -> Unit): Unit =
        this.let(builder)

    public fun build(): Content = Content(nodes)
}

public class ParagraphBuilder: Markable {
    public val nodes: MutableList<Node> = mutableListOf()

    public operator fun Node.unaryPlus() {
        nodes.add(this)
    }

    public operator fun List<Node>.unaryPlus() {
        nodes.addAll(this)
    }

    override fun Node.Paragraph.Text.Leaf.unaryPlus() {
        nodes.add(Node.Paragraph.Text(listOf(this)))
    }

    public operator fun String.unaryPlus() {
        nodes.add(text { + this@unaryPlus })
    }

    public fun text(builder: LeavesBuilder.() -> Unit): Node.Paragraph.Text {
        return Node.Paragraph.Text(
            LeavesBuilder()
                .apply(builder)
                .leaves
        )
    }

    public fun link(url: String): Node.Paragraph.Link = Node.Paragraph.Link(link = url)

    public fun reaction(id: Int): Node.Paragraph.Reaction = Node.Paragraph.Reaction(id = id)

    @Deprecated("This method requires you to provide text before and after the reaction", replaceWith = ReplaceWith("reaction(id)"))
    public fun reaction(before: String = "", id: Int, after: String = ""): List<Node> =
        listOf(text { +before }, Node.Paragraph.Reaction(id = id), text { +after })

    @Deprecated("This method requires you to provide text before and after the link", replaceWith = ReplaceWith("reaction(id)"))
    public fun link(before: String = "", url: String, after: String = ""): List<Node> =
        listOf(Node.Paragraph(content = listOf(text { +before }, Node.Paragraph.Link(link = url), text { +after })))
}

public class LeavesBuilder: Markable {
    public val leaves: MutableList<Node.Paragraph.Text.Leaf> = mutableListOf()

    public override operator fun Node.Paragraph.Text.Leaf.unaryPlus() {
        leaves.add(this)
    }

    public operator fun String.unaryPlus() {
        leaves.add(Node.Paragraph.Text.Leaf(this))
    }
}

public interface Markable {
    public fun String.bold(): Node.Paragraph.Text.Leaf = mark(RawMessageContentNodeLeavesMarkType.BOLD)
    public fun String.italic(): Node.Paragraph.Text.Leaf = mark(RawMessageContentNodeLeavesMarkType.ITALIC)
    public fun String.underline(): Node.Paragraph.Text.Leaf = mark(RawMessageContentNodeLeavesMarkType.UNDERLINE)
    public fun String.strikethrough(): Node.Paragraph.Text.Leaf = mark(RawMessageContentNodeLeavesMarkType.STRIKETHROUGH)
    public fun String.inlineCode(): Node.Paragraph.Text.Leaf = mark(RawMessageContentNodeLeavesMarkType.INLINE_CODE)
    public fun String.spoiler(): Node.Paragraph.Text.Leaf = mark(RawMessageContentNodeLeavesMarkType.SPOILER)

    public fun Node.Paragraph.Link.bold(): Node.Paragraph.Link = mark(RawMessageContentNodeLeavesMarkType.BOLD)
    public fun Node.Paragraph.Link.italic(): Node.Paragraph.Link = mark(RawMessageContentNodeLeavesMarkType.ITALIC)
    public fun Node.Paragraph.Link.underline(): Node.Paragraph.Link = mark(RawMessageContentNodeLeavesMarkType.UNDERLINE)
    public fun Node.Paragraph.Link.strikethrough(): Node.Paragraph.Link = mark(RawMessageContentNodeLeavesMarkType.STRIKETHROUGH)
    public fun Node.Paragraph.Link.inlineCode(): Node.Paragraph.Link = mark(RawMessageContentNodeLeavesMarkType.INLINE_CODE)
    public fun Node.Paragraph.Link.spoiler(): Node.Paragraph.Link = mark(RawMessageContentNodeLeavesMarkType.SPOILER)

    public fun Node.Paragraph.Text.Leaf.bold(): Node.Paragraph.Text.Leaf = mark(RawMessageContentNodeLeavesMarkType.BOLD)
    public fun Node.Paragraph.Text.Leaf.italic(): Node.Paragraph.Text.Leaf = mark(RawMessageContentNodeLeavesMarkType.ITALIC)
    public fun Node.Paragraph.Text.Leaf.underline(): Node.Paragraph.Text.Leaf = mark(RawMessageContentNodeLeavesMarkType.UNDERLINE)
    public fun Node.Paragraph.Text.Leaf.strikethrough(): Node.Paragraph.Text.Leaf = mark(RawMessageContentNodeLeavesMarkType.STRIKETHROUGH)
    public fun Node.Paragraph.Text.Leaf.inlineCode(): Node.Paragraph.Text.Leaf = mark(RawMessageContentNodeLeavesMarkType.INLINE_CODE)
    public fun Node.Paragraph.Text.Leaf.spoiler(): Node.Paragraph.Text.Leaf = mark(RawMessageContentNodeLeavesMarkType.SPOILER)

    public operator fun Node.Paragraph.Text.Leaf.unaryPlus()

    public fun String.mark(vararg marks: RawMessageContentNodeLeavesMarkType): Node.Paragraph.Text.Leaf {
        return Node.Paragraph.Text.Leaf(this, marks.toList())
    }
    public fun Node.Paragraph.Link.mark(vararg marks: RawMessageContentNodeLeavesMarkType): Node.Paragraph.Link {
        return Node.Paragraph.Link(link, Node.Paragraph.Text.Leaf(leaf.text, leaf.marks + marks))
    }
    public fun Node.Paragraph.Text.Leaf.mark(vararg marks: RawMessageContentNodeLeavesMarkType): Node.Paragraph.Text.Leaf {
        return Node.Paragraph.Text.Leaf(this.text, this.marks + marks)
    }
}

public fun contentBuilder(builder: ContentBuilder.() -> Unit): Content =
    ContentBuilder().apply(builder).build()