import org.jetbrains.dokka.gradle.DokkaPlugin

plugins {
    kotlin("jvm") version Dependencies.KotlinVersion
    kotlin("plugin.serialization") version Dependencies.KotlinVersion
    id("org.jetbrains.dokka") version Dependencies.DokkaVersion
    `maven-publish`
}

subprojects {
    group = "io.github.deck"
    version = Dependencies.Version
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