plugins {
    kotlin("jvm") version Dependencies.KotlinVersion
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

subprojects {
    apply<MavenPublishPlugin>()
    group = "com.guildedkt"
    version = Dependencies.Version

    publishing {
        repositories {
            maven {
                name = "GuildedKt"
                url = uri("https://maven.pkg.github.com/SrGaabriel/guilded-kt")

                credentials {
                    username = System.getProperty("USERNAME") ?: System.getenv("USERNAME")
                    password = System.getProperty("TOKEN") ?: System.getenv("TOKEN")
                }
            }
        }
    }
}