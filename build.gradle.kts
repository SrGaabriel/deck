plugins {
    kotlin("jvm") version Dependencies.KotlinVersion

}

subprojects {
    group = "com.deck"
    version = Dependencies.Version
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}