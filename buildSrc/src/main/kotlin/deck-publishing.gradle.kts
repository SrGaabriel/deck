import java.util.Base64

plugins {
    kotlin("jvm")
    `maven-publish`
    signing
}

tasks {
    val sourcesJar by registering(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    val docsJar by registering(Jar::class) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        description = "Javadocs"
        archiveExtension.set("javadoc")
        from(javadoc)
        dependsOn(javadoc)
    }

    publishing {
        publications {
            create<MavenPublication>("Deck") {
                groupId = Library.Group
                version = Library.Version

                from(components["java"])
                artifact(sourcesJar.get())
                artifact(docsJar.get())

                pom {
                    name.set("Deck")
                    description.set("A powerful and simple-to-use guilded wrapper made in Kotlin. ")
                    url.set(Library.Url)

                    developers {
                        developer {
                            name.set("SrGaabriel")
                            email.set("srgaabreil@protonmail.com")
                        }
                    }

                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://opensource.org/licenses/mit-license.php")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/SrGaabriel/deck.git")
                        developerConnection.set("scm:git:ssh://github.com:SrGaabriel/deck.git")
                        url.set("https://github.com/SrGaabriel/deck")
                    }
                }

                repositories {
                    maven(Library.ReleasesRepository) {
                        credentials {
                            username = System.getenv("NEXUS_USER")
                            password = System.getenv("NEXUS_PASSWORD")
                        }
                    }
                }
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    if (signingKey != null && signingPassword != null)
        useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["Deck"])
}