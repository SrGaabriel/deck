package io.github.srgaabriel.deck.rest.util

import io.github.srgaabriel.deck.common.util.OptionalProperty
import io.github.srgaabriel.deck.rest.builder.RequestBuilder
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KProperty

public class RequiredRequestField<A> {
    private var value: OptionalProperty<A> = OptionalProperty.NotPresent

    public operator fun getValue(thisRef: Any, property: KProperty<*>): A =
        if (value is OptionalProperty.Present<A>) (value as OptionalProperty.Present<A>).value else error("You must provide a value to the ${property.name} field!")

    public operator fun setValue(thisRef: Any, property: KProperty<*>, value: A) {
        this.value = OptionalProperty.Present(value)
    }
}

@Suppress("unused")
public fun <A> RequestBuilder<*>.required(): RequiredRequestField<A> = RequiredRequestField()

@OptIn(ExperimentalContracts::class)
internal fun String.plusIf(condition: Boolean, text: () -> String): String {
    contract {
        callsInPlace(text, InvocationKind.AT_MOST_ONCE)
    }
    return if (condition) this + text() else this
}