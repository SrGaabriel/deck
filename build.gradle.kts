import net.researchgate.release.GitAdapter

plugins {
    kotlin("jvm") version Dependencies.KotlinVersion
    id("net.researchgate.release") version "2.6.0"

}

subprojects {
    group = "com.deck"
    version = Dependencies.Version
    plugins.apply("net.researchgate.release")

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class) {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf("-Xexplicit-api=strict", "-opt-in=kotlin.RequiresOptIn")
            jvmTarget = "1.8"
        }
    }
    release {
        failOnUnversionedFiles = false
        with(propertyMissing("git") as GitAdapter.GitConfig) {
            requireBranch = """master|development|"""
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}