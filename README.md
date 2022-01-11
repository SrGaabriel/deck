![GitHub Workflow Status](https://img.shields.io/github/workflow/status/SrGaabriel/deck/Build)
![GitHub issues](https://img.shields.io/github/issues/SrGaabriel/deck)
![GitHub Repo stars](https://img.shields.io/github/stars/SrGaabriel/deck)

# 🎲 deck [WIP]
 Deck is a powerful yet simple-to-use guilded wrapper made entirely in Kotlin with support to multiplatform. 

## Implementating

In case you're using gradle, you just need to add this to your `build.gradle.kts`:

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.SrGaabriel:deck:-SNAPSHOT")
}
```

Or with maven:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependencies>
    <dependency>
        <groupId>com.github.SrGaabriel</groupId>
        <artifactId>deck</artifactId>
        <version>-SNAPSHOT</version>
    </dependency>
</dependencies>
```

## Example

This is a working example of deck's REST api:

```kotlin
suspend fun main() {
    val restClient = RestClient().authenticate {
        email = "email"
        password = "password"
    }
    val channelRoute = ChannelRoute(restClient)
    val message = channelRoute.sendMessage(channelId) {
        content {
            add text "Hello, World!"
            add image "image_url"
            add text "Goodbye, World!"
        }
    }
    println("Sent Message Id: ${message.id}")
}
```