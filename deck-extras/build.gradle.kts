plugins {
    kotlin("jvm")
    `deck-publishing`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":deck-core"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependencies.KotlinVersion}")
}