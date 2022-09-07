plugins {
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
    implementation(libs.logback)

    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
    testImplementation(libs.valiktor.core)
}