rootProject.name = "deck"

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

include("deck-common")
include("deck-rest")
include("deck-gateway")
include("deck-core")
include("deck-extras")