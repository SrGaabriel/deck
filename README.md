![GitHub Workflow Status](https://img.shields.io/github/workflow/status/SrGaabriel/deck/Build)
![GitHub issues](https://img.shields.io/github/issues/SrGaabriel/deck)
![GitHub Repo stars](https://img.shields.io/github/stars/SrGaabriel/deck)

# 🎲 deck [WIP]
 Deck is a powerful yet simple-to-use guilded wrapper made entirely in Kotlin with supporting multiplatform. Take a look at
an example of the Rest API:

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