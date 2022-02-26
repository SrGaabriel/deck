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
                api(project(":deck-common"))
                implementation(kotlin("stdlib-common"))
            }
            explicitApi()
        }
    }
}