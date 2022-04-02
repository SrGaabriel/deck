![GitHub Workflow Status](https://img.shields.io/github/workflow/status/SrGaabriel/deck/Build)
![GitHub issues](https://img.shields.io/github/issues/SrGaabriel/deck)
![GitHub Repo stars](https://img.shields.io/github/stars/SrGaabriel/deck)

# 🎲 deck [WIP]

Deck is a powerful yet simple-to-use guilded wrapper made entirely in Kotlin with support to multiplatform.

This library is meant for the Client API (self-bots), it does not cover the early Official Bot Api. In the future we plan to support both APIs, but in case that is deemed impossible or an expensive task, we'll drop support to the Client API.

## Documentation

You can find deck's documentation [here](https://github.com/SrGaabriel/deck/wiki). Although incomplete, it covers some little more complex topics. If you want to contribute with the wiki, feel more than free to do so.

If problems arise while using the API, you can either open a [GitHub Issue](https://github.com/SrGaabriel/deck/issues/new), or contact me in either my Discord (gaabriel#1911) or my Guilded (SrGaabriel).

## Implementating

You can implement and use deck in your project by adding Jitpack to your build repositories in your `build.gradle.kts`...

```kotlin
repositories {
    maven("https://jitpack.io")
}
```

And then add deck dependency with the latest version: `0.0.3-BETA`.

```kotlin
dependencies {
    implementation("com.github.SrGaabriel.deck:deck-core:$deckVersion")
}
```

Just by adding these to your build script, you'll already be able to use deck in your project.

## Thanks

We want to specially thank the discord Kotlin wrapper library [Kord](https://github.com/kordlib/kord), since deck's structure was inspired/based around it.

There's also [Guilded API](https://guildedapi.com/), the unofficial documentation for Guilded's API. It's all thanks to them that Deck has many features at a very young age.
