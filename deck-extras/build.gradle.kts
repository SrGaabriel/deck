plugins {
    `deck-publishing`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":deck-core"))
    implementation(libs.mordant)
}