package com.deck.common.util

// Todo: Improve Support
public data class Emoji(
    public val id: Int,
    public val name: String,
    public val category: String,
    public val order: Int,
    public val aliases: Array<String>,
    public val isAnimated: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Emoji

        if (id != other.id) return false
        if (name != other.name) return false
        if (category != other.category) return false
        if (order != other.order) return false
        if (!aliases.contentEquals(other.aliases)) return false
        if (isAnimated != other.isAnimated) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + order
        result = 31 * result + aliases.contentHashCode()
        result = 31 * result + isAnimated.hashCode()
        return result
    }
}