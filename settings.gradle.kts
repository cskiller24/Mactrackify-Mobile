pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            credentials {
                username = "mapbox"
                password = "pk.eyJ1IjoiY3NraWxsZXIyNCIsImEiOiJjbG56ajNqaDgweDZoMnFwOWwzcmsyeW1lIn0.3m7Ea1fh5uzvBn_XEE4p5A"
            }
        }
    }
}

rootProject.name = "MactrackifyAlpha"
include(":app")
 