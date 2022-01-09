package com.deck.common.util

/**
 * This implies that the annotated value has an unknown meaning
 * to the platform. In other words, it's useless and should not be used unless you know what you're doing.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER)
annotation class DeckUnknown

/**
 * This implies that the annotated value isn't supported yet by the library,
 * meaning that it's known but was not adapted and isn't ready for use.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY)
annotation class DeckUnsupported

@DslMarker
annotation class DeckDSL