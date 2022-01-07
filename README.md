<img align="right" src="./.github/logo.png" style="border-radius: 32px; display: block;text-align:center;margin: 0 auto" alt="Guilded KT" width=128px height=128px/>

# ⏰ guilded-kt [WIP]
 A powerful yet simple-to-use guilded wrapper made entirely in Kotlin with supporting multiplatform. Take a look at
an example of the Rest API:

```kotlin
suspended fun main() {
    val restClient = RestClient().authenticate {
        email = "email"
        password = "password"
    }
    val channelRoute = ChannelRoute(restClient)
    val message = channelRoute.sendMessage(channelId) {
        content {
            add text "Hello, World!"
            add image image_url
            add text "Goodbye, World!"
        }
    }
    println("Sent Message Id: ${message.id}")
}
```
