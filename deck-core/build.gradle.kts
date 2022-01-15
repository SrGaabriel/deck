plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    api(project(":deck-common"))
    api(project(":deck-rest"))
    api(project(":deck-gateway"))
    implementation("io.ktor:ktor-client-core:${Dependencies.KtorVersion}")
    implementation("io.ktor:ktor-client-cio:${Dependencies.KtorVersion}")
    implementation("io.ktor:ktor-client-websockets:${Dependencies.KtorVersion}")
    implementation("io.ktor:ktor-client-serialization:${Dependencies.KtorVersion}")
    implementation("io.github.microutils:kotlin-logging-jvm:${Dependencies.KotlinLoggingVersion}")
}
