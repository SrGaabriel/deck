package com.deck.common.content

import com.deck.common.content.node.Node
import com.deck.common.util.DeckExperimental

public class Content {
    public val nodes: MutableList<Node> = mutableListOf()

    public var leaves: List<String>
        get() = nodes.filterIsInstance<Node.Text>().mapNotNull { it.data.text }
        set(value) { nodes.removeAll { it is Node.Image }.let { nodes.addAll(value.map { Node.Text(text = it) }) } }
    public var images: List<String>
        get() = nodes.filterIsInstance<Node.Image>().mapNotNull { it.data.image }
        set(value) { nodes.removeAll { it is Node.Image }.let { nodes.addAll(value.map { Node.Image(image = it) }) } }

    public var text: String
        get() = leaves.joinToString("\n")
        @DeckExperimental
        set(value) { leaves = listOf(value) }

    public fun addNode(node: Node): Unit = nodes.add(node).let {}

    public fun addText(text: String): Unit = addNode(Node.Text(text = text))

    public fun addImage(image: String): Unit = addNode(Node.Image(image = image))
}
