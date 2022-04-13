package com.deck.common.util

/**
 * This implies that the annotated value has an unknown meaning
 * to the platform. In other words, it's useless and should not be used unless you know what you're doing.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER)
public annotation class DeckUnknown

/**
 * This implies that the annotated value isn't supported yet by the library,
 * meaning that it's known but was not adapted and isn't ready for use.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.PROPERTY)
@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
public annotation class DeckUnsupported

/**
 * Marks a feature as experimental, meaning they might contain errors
 * and shouldn't be used with a large bot unless you know what you're doing.
 */
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY_SETTER
)
@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
public annotation class DeckExperimental

/**
 * Marks a feature as delicate, indicating that it might break
 * anytime, and should be carefully studied/inspected before use.
 */
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY_SETTER
)
@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
public annotation class DeckDelicateApi

/**
 * Marks a feature as obsolete, indicating that it's deprecated,
 * but doesn't have an actual replacement yet.
 */
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY_SETTER
)
@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
public annotation class DeckObsoleteApi

/**
 * Marks the feature as internal, in other words, you should only use this feature
 * if there's no public replacement.
 */
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY_SETTER
)
@RequiresOptIn(level = RequiresOptIn.Level.ERROR)
public annotation class DeckInternalApi

@DslMarker
public annotation class DeckDSL