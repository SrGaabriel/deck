package io.github.deck.extras

import io.github.deck.core.DeckClient
import io.github.deck.core.event.message.DeckMessageCreateEvent
import io.github.deck.core.util.on
import io.github.deck.extras.content.sendContent

public suspend fun main() {
    val client = DeckClient("JAb2jEXakXzsIyAqZ29CLpqPMg1E4knXTjzsrSRUMe4FXE3dMZqlUS9m4OQKDcdUVMQ7vPy8H6m3avvzp1Kzag==")
    client.on<DeckMessageCreateEvent> {
        channel.sendContent {
            embed {
                description = """
                    <@$authorId>
                """.trimIndent()
            }
        }
    }
    client.login()
}