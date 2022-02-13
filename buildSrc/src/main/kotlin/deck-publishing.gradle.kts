plugins {
    `maven-publish`
}

tasks {
    publishing {
        publications {
            create<MavenPublication>("Deck") {
                groupId = "com.deck"
                artifactId = project.name
                version = Dependencies.Version
                from(components["kotlin"])

                pom {
                    name.set("Deck")
                    description.set("A powerful and simple-to-use guilded wrapper made in Kotlin. ")
                }
            }
        }
    }
}