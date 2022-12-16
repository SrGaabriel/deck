package io.github.srgaabriel.deck.common.log

public typealias LoggingMessage = () -> Any

public interface DeckLogger {
    public fun log(
        level: LoggingLevel,
        message: LoggingMessage,
        exception: Throwable? = null
    )
}

public enum class LoggingLevel {
    Debug,
    Info,
    Warning,
    Error
}


@DslMarker
public annotation class LoggingDsl

@LoggingDsl
@Suppress("NOTHING_TO_INLINE")
public inline fun DeckLogger.debug(exception: Throwable? = null, noinline message: LoggingMessage): Unit =
    log(LoggingLevel.Debug, message, exception)

@LoggingDsl
@Suppress("NOTHING_TO_INLINE")
public inline fun DeckLogger.info(exception: Throwable? = null, noinline message: LoggingMessage): Unit =
    log(LoggingLevel.Info, message, exception)

@LoggingDsl
@Suppress("NOTHING_TO_INLINE")
public inline fun DeckLogger.warning(exception: Throwable? = null, noinline message: LoggingMessage): Unit =
    log(LoggingLevel.Warning, message, exception)

@LoggingDsl
@Suppress("NOTHING_TO_INLINE")
public inline fun DeckLogger.error(exception: Throwable? = null, noinline message: LoggingMessage): Unit =
    log(LoggingLevel.Error, message, exception)