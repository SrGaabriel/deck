package com.deck.core.util

import com.deck.common.util.OptionalProperty

public sealed class Difference<out T> {
    public object Unchanged: Difference<Nothing>()

    public object Null: Difference<Nothing>()

    public data class Value<T>(val value: T): Difference<T>()

    override fun toString(): String = when(this) {
        is Unchanged -> "Unchanged"
        is Null -> "Reset"
        is Value<*> -> value.toString()
    }
}

public fun <T> OptionalProperty<T?>.asDifference(): Difference<T> = when(this) {
    is OptionalProperty.NotPresent -> Difference.Unchanged
    is OptionalProperty.Present -> {
        if (value == null) Difference.Null else Difference.Value(value!!)
    }
}

public infix fun <T : Any> Difference<T>.or(value: T): T = when (this) {
    is Difference.Value<T> -> this.value
    else -> value
}

public infix fun <T> Difference<T>.nullableOr(value: T?): T? = when (this) {
    is Difference.Value<T> -> this.value
    is Difference.Null -> null
    else -> value
}

public fun <T> Difference<T>.asNullable(): T? = when (this) {
    is Difference.Value<T> -> value
    else -> null
}