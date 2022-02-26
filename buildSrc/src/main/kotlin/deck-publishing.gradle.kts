plugins {
    `maven-publish`
}

tasks {
    publishing {
        publications {
            create<MavenPublication>("Deck") {
                groupId = "com.deck"
                version = Dependencies.Version
                from(components["kotlin"])

                pom {
                    name.set("Deck")
                    description.set("A powerful and simple-to-use guilded wrapper made in Kotlin. ")
                }
            }
        }
        publications.withType<MavenPublication>().all {
            artifactId = "client-${project.name}"
        }
    }
}