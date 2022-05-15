package io.github.deck.common.util

import io.github.deck.common.log.DeckLogger
import io.github.deck.common.log.LoggingLevel
import io.github.deck.common.log.LoggingMessage
import mu.KLoggable
import mu.KLogger

public class MicroutilsLogger(name: String): DeckLogger, KLoggable {
    override val logger: KLogger = logger(name)

    override fun log(level: LoggingLevel, message: LoggingMessage, exception: Throwable?) {
        when (level) {
            LoggingLevel.Info -> logger.info(exception, message)
            LoggingLevel.Debug -> logger.debug(exception, message)
            LoggingLevel.Warning -> logger.warn(exception, message)
            LoggingLevel.Error -> logger.error(exception, message)
        }
    }
}