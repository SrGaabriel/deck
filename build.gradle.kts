plugins {
    kotlin("jvm") version Dependencies.KotlinVersion
}

allprojects {
    group = "com.guildedkt"
    version = "0.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}
