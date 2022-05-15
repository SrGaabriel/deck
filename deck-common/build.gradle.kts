plugins {
    kotlin("multiplatform")
    `deck-publishing`
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
            }
            explicitApi()
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.github.microutils:kotlin-logging-jvm:${Dependencies.KotlinLoggingVersion}")
            }
        }
    }
}