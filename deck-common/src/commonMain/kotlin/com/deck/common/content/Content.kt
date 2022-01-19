package com.deck.common.content

import com.deck.common.content.node.Node

class Content {
    val nodes: MutableList<Node> = mutableListOf()

    var leaves: List<String>
        get() = nodes.filterIsInstance<Node.Text>().mapNotNull { it.data.text }
        set(value) { nodes.removeAll { it is Node.Image }.let { nodes.addAll(value.map { Node.Text(text = it) }) } }
    var images: List<String>
        get() = nodes.filterIsInstance<Node.Image>().mapNotNull { it.data.image }
        set(value) { nodes.removeAll { it is Node.Image }.let { nodes.addAll(value.map { Node.Image(image = it) }) } }

    var text: String
        get() = leaves.joinToString("\n")
        set(value) { leaves = listOf(value) }

    fun addNode(node: Node): Unit = nodes.add(node).let {}
}
