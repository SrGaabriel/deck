plugins {
    `deck-publishing`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    api(project(":deck-common"))
    implementation(libs.bundles.ktor.client.essentials)
    implementation(libs.ktor.client.websockets)
    implementation(libs.kotlin.logging.jvm)
}