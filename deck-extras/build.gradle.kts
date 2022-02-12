plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":deck-core"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependencies.KotlinVersion}")
}