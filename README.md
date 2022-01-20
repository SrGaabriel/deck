![GitHub Workflow Status](https://img.shields.io/github/workflow/status/SrGaabriel/deck/Build)
![GitHub issues](https://img.shields.io/github/issues/SrGaabriel/deck)
![GitHub Repo stars](https://img.shields.io/github/stars/SrGaabriel/deck)

# 🎲 deck [WIP]

Deck is a powerful yet simple-to-use guilded wrapper made entirely in Kotlin with support to multiplatform.

## Example

This is a working example of deck's REST api:

```kotlin
suspend fun main() {
    val client: DeckClient = client {
        email = "email"
        password = "password"
    }
    client.on<DeckMessageCreateEvent> {
        if (!message.content.text.startsWith("+ping"))
            return@on
        val previousMessage: Message = channel.sendMessage {
            content.text = "Pong!"
        }
        previousMessage.sendReply {
            content.text = "quick, come up with something funny for the example"
        }
    }
    client.login()
}
```

## Implementating

We do not have an official repository/artifact, so the only available way is to compile the library yourself and implement it.

## Thanks

We want to specially thank the discord Kotlin wrapper library [Kord](https://github.com/kordlib/kord), since deck's structure was inspired/based around it.
