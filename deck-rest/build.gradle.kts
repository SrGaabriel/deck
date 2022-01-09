plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version Dependencies.KotlinVersion
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
}