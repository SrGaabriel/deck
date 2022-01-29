![GitHub Workflow Status](https://img.shields.io/github/workflow/status/SrGaabriel/deck/Build)
![GitHub issues](https://img.shields.io/github/issues/SrGaabriel/deck)
![GitHub Repo stars](https://img.shields.io/github/stars/SrGaabriel/deck)

# 🎲 deck [WIP]

This is the official bot API deck implementation, which is still in an early and experimental phase. We do not recommend using this version of deck, but rather use the client API implementation, which can be found here[]

## Example

We have supported and wrapped all existing routes within guilded's bot API, we are currently waiting API updates.

```kotlin
suspend fun main() {
    val client = DeckClient("token")
    client.on<DeckMessageCreateEvent> {
        if (!message.content.startsWith("+hello"))
            return@on
        val message: Message = channel.sendMessage("Hello, World!")
        message.sendReply("Hello, this is a reply!")
    }
    client.login()
}
```

As you may have already noticed, the syntax is pretty much the same as in the client API. We don't guarantee that this will hold forever, but we'll try to make the library as intuitive as possible.