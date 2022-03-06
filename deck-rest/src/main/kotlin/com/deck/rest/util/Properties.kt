package com.deck.rest.util

import com.deck.common.util.OptionalProperty
import com.deck.rest.builder.RequestBuilder
import kotlin.reflect.KProperty

public class RequiredRequestField<A> {
    private var value: OptionalProperty<A> = OptionalProperty.NotPresent

    public operator fun getValue(thisRef: Any, property: KProperty<*>): A =
        if (value is OptionalProperty.Present<A>) (value as OptionalProperty.Present<A>).value else error("You must provide a value to the ${property.name} field!")

    public operator fun setValue(thisRef: Any, property: KProperty<*>, value: A) {
        this.value = OptionalProperty.Present(value)
    }
}

public fun <A> RequestBuilder<*>.required(): RequiredRequestField<A> = RequiredRequestField()