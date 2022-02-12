package com.deck.common.util

public data class CustomReaction(
    val id: IntGenericId,
    val name: String,
    val png: String?,
    val webp: String?,
    val apng: String?
)

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
        if (other == null) return false

        if (other is Emoji) return other.id == id
        else if (other is Number) return other == id
        else return false
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