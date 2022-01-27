plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":deck-common"))
                implementation(kotlin("stdlib-common"))
            }
            explicitApi()
        }
    }
}