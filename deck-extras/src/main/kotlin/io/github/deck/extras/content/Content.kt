package io.github.deck.extras.content

import io.github.deck.common.Embed
import io.github.deck.common.EmbedBuilder
import io.github.deck.common.util.DeckDelicateApi
import io.github.deck.common.util.Emoji
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.extras.content.node.Node
import kotlinx.serialization.json.JsonPrimitive
import java.util.*

public class Content(public val nodes: List<Node> = emptyList()) {
    public val paragraphs: List<Node.Paragraph>
        get() = nodes.filterIsInstance<Node.Paragraph>()
    public val leaves: List<Node.Paragraph.Text.Leaf>
        get() = paragraphs.flatMap { it.data.children }.filter { it.data.leaves != null }.flatMap { it.data.leaves!! }
    public val links: List<Node.Paragraph.Link>
        get() = paragraphs.flatMap { it.data.children }.filterIsInstance<Node.Paragraph.Link>()
    public val reactions: List<IntGenericId>
        get() = paragraphs.flatMap { it.data.children }.filterIsInstance<Node.Paragraph.Reaction>().map { it.id }
    public val texts: List<String>
        get() = leaves.map { it.text }
    public val images: List<String>
        get() = nodes.filterIsInstance<Node.Image>().map { it.image }
    public val embeds: List<Embed>
        get() = nodes.filterIsInstance<Node.Embed>().flatMap { it.embeds }
    public val lists: List<Node.Lists>
        get() = nodes.filterIsInstance<Node.Lists>()
    public val codeBlocks: List<Node.CodeBlock>
        get() = nodes.filterIsInstance<Node.CodeBlock>()
    public val quoteBlocks: List<Node.Quote>
        get() = nodes.filterIsInstance<Node.Quote>()
    public val text: String
        get() = texts.joinToString("\n")
    public val mentions: List<Node.Mention>
        get() = paragraphs.flatMap { it.data.children }.filterIsInstance<Node.Mention>()
    public val rawLinks: List<String>
        get() = links.map { it.link }
}

public class ContentBuilder(private val quoteContainer: Boolean = false): Markable {
    private val nodes: MutableList<Node> = mutableListOf()

    public operator fun Node.unaryPlus() {
        nodes.add(this)
    }

    public operator fun Iterable<Node>.unaryPlus() {
        nodes.addAll(this)
    }

    override fun Node.Paragraph.Text.Leaf.unaryPlus() {
        nodes.add(Node.Paragraph(content = listOf(Node.Paragraph.Text(listOf(this))), insideQuoteBlock = quoteContainer))
    }

    public operator fun String.unaryPlus() {
        nodes.add(Node.Paragraph(listOf(text(this)), insideQuoteBlock = quoteContainer))
    }

    public operator fun Embed.unaryPlus() {
        nodes.add(Node.Embed(embeds = listOf(this)))
    }

    public operator fun Collection<Embed>.unaryPlus() {
        nodes.add(Node.Embed(embeds = this.toList()))
    }

    public operator fun Emoji.unaryPlus() {
        nodes.add(Node.Paragraph.Reaction(id))
    }

    public fun codeblock(language: String, text: String) {
        nodes.add(Node.CodeBlock(lines = text.lines().map { Node.CodeBlock.Line(it) }, language = language.lowercase()))
    }

    public fun codeblock(language: String = "unformatted", builder: CodeBlockBuilder.() -> Unit) {
        + Node.CodeBlock(language.lowercase(),
            CodeBlockBuilder()
                .apply(builder)
                .lines
        )
    }

    public fun paragraph(builder: ParagraphBuilder.() -> Unit) {
        + Node.Paragraph(
            ParagraphBuilder()
                .apply(builder)
                .nodes,
            insideQuoteBlock = quoteContainer
        )
    }

    public fun quote(builder: ContentBuilder.() -> Unit) {
        + Node.Quote(
            ContentBuilder(quoteContainer = true)
                .apply(builder)
                .nodes
        )
    }

    public fun bulletedList(builder: ListBuilder.() -> Unit) {
        + Node.Lists.Bulleted(
            ListBuilder()
                .apply(builder)
                .items
        )
    }

    public fun numberedList(builder: ListBuilder.() -> Unit) {
        + Node.Lists.Numbered(
            ListBuilder()
                .apply(builder)
                .items
        )
    }

    public fun text(builder: LeavesBuilder.() -> Unit) {
        + Node.Paragraph.Text(
            LeavesBuilder()
                .apply(builder)
                .leaves
        )
    }

    public fun text(text: String, marks: List<RawMessageContentNodeLeavesMarkType> = emptyList()): Node.Paragraph.Text =
        Node.Paragraph.Text(leaves = listOf(Node.Paragraph.Text.Leaf(text, marks)))

    public fun embed(builder: EmbedBuilder.() -> Unit) {
        + Node.Embed(listOf(EmbedBuilder().apply(builder).build()))
    }

    @DeckDelicateApi
    /**
     * This method only attaches the specified message if it's
     * from guilded's CDN, so to attach a separate image, you need to upload it via rest.
     */
    public fun image(url: String): Node.Image = Node.Image(image = url)

    public fun user(before: String = "", id: GenericId, after: String = ""): Node.Paragraph =
        Node.Paragraph(content = listOf(text(before), Node.Mention(id = JsonPrimitive(id), mentionType = RawMentionType.USER), text(after)), insideQuoteBlock = quoteContainer)

    public fun role(before: String = "", id: IntGenericId, after: String = ""): Node.Paragraph =
        Node.Paragraph(content = listOf(text(before), Node.Mention(id = JsonPrimitive(id), mentionType = RawMentionType.ROLE), text(after)), insideQuoteBlock = quoteContainer)

    public fun channel(before: String = "", id: UUID, after: String = ""): Node.Paragraph =
        Node.Paragraph(content = listOf(text(before), Node.Mention.Channel(id = id), text(after)), insideQuoteBlock = quoteContainer)

    public fun reaction(before: String = "", id: Int, after: String = ""): Node.Paragraph =
        Node.Paragraph(content = listOf(text(before), Node.Paragraph.Reaction(id = id), text(after)), insideQuoteBlock = quoteContainer)

    public fun link(before: String = "", url: String, after: String = "", text: String = url): Node.Paragraph =
        Node.Paragraph(content = listOf(text(before), Node.Paragraph.Link(link = url, text), text(after)), insideQuoteBlock = quoteContainer)

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
        nodes.add(text(this))
    }

    public operator fun Emoji.unaryPlus() {
        nodes.add(Node.Paragraph.Reaction(id))
    }

    public fun text(text: String, marks: List<RawMessageContentNodeLeavesMarkType> = emptyList()): Node.Paragraph.Text =
        Node.Paragraph.Text(leaves = listOf(Node.Paragraph.Text.Leaf(text, marks)))

    public fun text(builder: LeavesBuilder.() -> Unit) {
        + Node.Paragraph.Text(
            LeavesBuilder()
                .apply(builder)
                .leaves
        )
    }

    public fun link(url: String, text: String = url): Node.Paragraph.Link = Node.Paragraph.Link(link = url, text = text)

    public fun reaction(id: Int): Node.Paragraph.Reaction = Node.Paragraph.Reaction(id = id)

    public fun user(id: GenericId): Node.Mention = Node.Mention(id = JsonPrimitive(id), mentionType = RawMentionType.USER)

    public fun role(id: IntGenericId): Node.Mention = Node.Mention(id = JsonPrimitive(id), mentionType = RawMentionType.ROLE)

    public fun channel(id: UUID): Node.Mention.Channel = Node.Mention.Channel(id = id)

    @Deprecated("This method requires you to provide text before and after the reaction", replaceWith = ReplaceWith("reaction(id)"))
    public fun reaction(before: String = "", id: Int, after: String = ""): List<Node> =
        listOf(text(before), Node.Paragraph.Reaction(id = id), text(after))

    @Deprecated("This method requires you to provide text before and after the link", replaceWith = ReplaceWith("link(string)"))
    public fun link(before: String = "", url: String, after: String = "", text: String = url): List<Node> =
        listOf(Node.Paragraph(content = listOf(text(before), Node.Paragraph.Link(link = url, text = text), text(after))))

    @Deprecated("This method requires you to provide an id before and after the mention", replaceWith = ReplaceWith("user(id)"))
    public fun user(before: String = "", id: GenericId, after: String = ""): List<Node> =
        listOf(Node.Paragraph(content = listOf(text(before), Node.Mention(id = JsonPrimitive(id), mentionType = RawMentionType.USER), text(after))))

    @Deprecated("This method requires you to provide an id before and after the mention", replaceWith = ReplaceWith("role(id)"))
    public fun role(before: String = "", id: IntGenericId, after: String = ""): List<Node> =
        listOf(Node.Paragraph(content = listOf(text(before), Node.Mention(id = JsonPrimitive(id), mentionType = RawMentionType.ROLE), text(after))))

    @Deprecated("This method requires you to provide an id before and after the mention", replaceWith = ReplaceWith("channel(id)"))
    public fun channel(before: String = "", id: UUID, after: String = ""): List<Node> =
        listOf(Node.Paragraph(content = listOf(text(before), Node.Mention.Channel(id = id), text(after))))
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

public class CodeBlockBuilder {
    public val lines: MutableList<Node.CodeBlock.Line> = mutableListOf()

    public operator fun String.unaryPlus() {
        lines.add(Node.CodeBlock.Line(this))
    }
}

public class ListBuilder {
    public val items: MutableList<Node.Lists.Item> = mutableListOf()

    public fun item(builder: ParagraphBuilder.() -> Unit) {
        items.add(
            Node.Lists.Item(
                ParagraphBuilder()
                    .apply(builder)
                    .nodes
            )
        )
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
        return Node.Paragraph.Link(link, text, Node.Paragraph.Text.Leaf(leaf.text, leaf.marks + marks))
    }
    public fun Node.Paragraph.Text.Leaf.mark(vararg marks: RawMessageContentNodeLeavesMarkType): Node.Paragraph.Text.Leaf {
        return Node.Paragraph.Text.Leaf(this.text, this.marks + marks)
    }
}

public fun contentBuilder(builder: ContentBuilder.() -> Unit): Content =
    ContentBuilder().apply(builder).build()