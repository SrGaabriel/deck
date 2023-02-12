![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/SrGaabriel/deck/build.yml?branch=development&style=for-the-badge)
![GitHub issues](https://img.shields.io/github/issues/SrGaabriel/deck?color=orange&style=for-the-badge)
![GitHub Repo stars](https://img.shields.io/github/stars/SrGaabriel/deck?color=yellow&style=for-the-badge)
![Nexus badge](https://img.shields.io/nexus/r/io.github.srgaabriel.deck/deck-core?color=%2329B472&server=https%3A%2F%2Fs01.oss.sonatype.org&style=for-the-badge)


# 🎲 deck [WIP]

This is the official bot API deck implementation, which is still in an early and experimental phase. We'll be adding and implementing features as Guilded releases new API updates.

## Example

Below is an example of deck's powerful API, mainly inspired by [kord](https://github.com/kordlib/kord).

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

To use deck in your project, you just need to add the dependency `com.github.SrGaabriel.deck:deck-core:$version` and make sure you have `mavenCentral` registered as a repository.

You can replace `deck-core` with the name of the module you wish to import, and for the version you may use the latest one:
![GitHub release (latest by date)](https://img.shields.io/github/v/release/SrGaabriel/deck?style=social)

```kotlin
dependencies {
    implementation("io.github.srgaabriel.deck:deck-core:$version")
}
```
