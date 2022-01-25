import net.researchgate.release.*

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
        failOnCommitNeeded = false
        failOnPublishNeeded = false
        with(propertyMissing("git") as GitAdapter.GitConfig) {
            requireBranch = """master|development|"""
        }
        with(propertyMissing("svn") as SvnAdapter.SvnConfig) {
            username = System.getProperty("USERNAME") ?: System.getenv("USERNAME")
            password = System.getProperty("TOKEN") ?: System.getenv("TOKEN")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}