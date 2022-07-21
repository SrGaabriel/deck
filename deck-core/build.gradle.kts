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
    api(project(":deck-rest"))
    api(project(":deck-gateway"))
    implementation("io.github.microutils:kotlin-logging-jvm:${Dependencies.KotlinLoggingVersion}")
    implementation("ch.qos.logback:logback-classic:1.2.11")
//    implementation("com.github.ben-manes.caffeine:caffeine:3.0.5")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependencies.KotlinVersion}")
}