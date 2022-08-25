import io.github.deck.core.DeckClient
import io.github.deck.core.event.message.MessageCreateEvent
import io.github.deck.core.util.sendReplyWithEmbed
import io.github.deck.extras.misc.coloredLogging

public suspend fun main() {
    val client = DeckClient("gapi_CC31UTtcqZS6ugYkQqP7hyS0jRvENNi24xvvW+SxT04jkwdX5/o2nCSdfU8JDVlyEwpkQ/AAUxblE8HQRYkfdw==") {
        coloredLogging()
    }
    client.on<MessageCreateEvent> {
        if (message.author.id == client.selfId)
            return@on
        message.sendReplyWithEmbed {
            description = "Hello, World!"
        }
    }
    client.login()
}