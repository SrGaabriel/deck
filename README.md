![GitHub Workflow Status](https://img.shields.io/github/workflow/status/SrGaabriel/deck/Build?style=for-the-badge)
![GitHub issues](https://img.shields.io/github/issues/SrGaabriel/deck?color=purple&style=for-the-badge)
![GitHub Repo stars](https://img.shields.io/github/stars/SrGaabriel/deck?color=orange&style=for-the-badge)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/SrGaabriel/deck?label=latest%20version&style=for-the-badge)

# 🎲 deck [WIP]

This is the official bot API deck implementation, which is still in an early and experimental phase. We'll be adding and implementing features according to the API rhythm.

## Example

Deck has a really powerful API, here is an example:

```kotlin
suspend fun main() {
    val client = DeckClient("token")
    client.on<MessageCreateEvent> {
        if (!message.content.startsWith("+hello"))
            return@on
        val message: Message = channel.sendMessage("Hello, World!")
        message.sendReply("Hello, this is a reply!")
    }
    client.login()
}
```

For tutorials and documentation, check out our [wiki](https://github.com/SrGaabriel/deck/wiki).

## Using

To use deck in your project, you **must** have the `jitpack` repository registered. And lastly, you're going to add the following dependency: `com.github.SrGaabriel.deck:deck-core:$version`.

You can replace `deck-core` with the name of the module you wish to import, and for the version you may use the latest one:
![GitHub release (latest by date)](https://img.shields.io/github/v/release/SrGaabriel/deck?style=social)

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.SrGaabriel.deck:deck-core:$version")
}
```
