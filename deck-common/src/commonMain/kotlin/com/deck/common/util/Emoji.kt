package com.deck.common.util

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
        if (other == null) return false

        return when (other) {
            is Emoji -> other.id == id
            is Number -> other == id
            else -> false
        }
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

    override fun toString(): String = ":$name:"
}