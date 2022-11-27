package io.github.deck.extras.misc

import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import io.github.deck.common.log.DeckLogger
import io.github.deck.common.log.LoggingLevel
import io.github.deck.common.log.LoggingMessage
import io.github.deck.core.util.ClientBuilder

/**
 * Enables colored logging
 */
public fun ClientBuilder.coloredLogging() {
    val logger = MordantLogger()
    rest.logger = logger
    gateway.logger = logger
}

public class MordantLogger: DeckLogger {
    private val terminal = Terminal(tabWidth = 4, ansiLevel = AnsiLevel.TRUECOLOR)

    override fun log(level: LoggingLevel, message: LoggingMessage, exception: Throwable?) {
        val (prefix, color) = when(level) {
            LoggingLevel.Debug -> "\uD83E\uDDEA debug" to TextColors.brightBlue
            LoggingLevel.Info -> "\uD83D\uDCE8 info" to TextColors.brightGreen
            LoggingLevel.Warning -> "⚠ warning" to TextColors.brightYellow
            LoggingLevel.Error -> "❌ error" to TextColors.brightRed
        }
        fun String.asFormattedText() = replace("\n", "\n${" ".repeat(prefix.length)}  ")

        val text = buildString {
            append(color(prefix))
            append(": ")
            val message = message()
            append(
                (if (message is Throwable)
                    message.stackTraceToString()
                else
                    terminal.render(message))
                .asFormattedText()
            )
            if (exception != null) {
                append(exception.stackTraceToString().asFormattedText())
            }
        }
        terminal.println(text)
    }
}