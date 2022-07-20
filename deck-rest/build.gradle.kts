plugins {
    kotlin("jvm")
    `deck-publishing`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    api(project(":deck-common"))
    implementation("io.ktor:ktor-client-core:${Dependencies.KtorVersion}")
    implementation("io.ktor:ktor-client-cio:${Dependencies.KtorVersion}")
    implementation("io.ktor:ktor-client-serialization:${Dependencies.KtorVersion}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${Dependencies.KtorVersion}")
    implementation("io.ktor:ktor-client-logging:${Dependencies.KtorVersion}")
    implementation("io.ktor:ktor-client-content-negotiation:${Dependencies.KtorVersion}")
    implementation("io.github.microutils:kotlin-logging-jvm:${Dependencies.KotlinLoggingVersion}")
}