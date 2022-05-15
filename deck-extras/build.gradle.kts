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
    implementation("com.github.ajalt.mordant:mordant:${Dependencies.MordantVersion}")
}