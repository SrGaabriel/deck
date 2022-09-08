import org.jetbrains.dokka.gradle.DokkaPlugin

@Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    `maven-publish`
    idea
}

subprojects {
    group = "io.github.deck"
    version = Library.Version
    apply<MavenPublishPlugin>()
    apply<DokkaPlugin>()
    apply<IdeaPlugin>()
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    tasks {
        withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class) {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf("-Xexplicit-api=strict", "-opt-in=kotlin.RequiresOptIn")
                jvmTarget = "1.8"
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true

        excludeDirs = excludeDirs + layout.files(
            ".idea",
            "gradle/wrapper"
        )
    }
}