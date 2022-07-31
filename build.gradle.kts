import org.jetbrains.dokka.gradle.DokkaPlugin

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("org.jetbrains.dokka") version "1.7.10"
    `maven-publish`
}

subprojects {
    group = "io.github.deck"
    version = Library.Version
    plugins.apply("org.jetbrains.kotlin.plugin.serialization")
    apply<MavenPublishPlugin>()
    apply<DokkaPlugin>()

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