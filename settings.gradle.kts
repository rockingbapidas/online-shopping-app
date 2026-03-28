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
    }
}

rootProject.name = "Amazon Sample"
include(":app")
include(":core:ui")
include(":core:network")
include(":core:database")
include(":core:common")
include(":feature:auth")
include(":feature:home")
include(":feature:product")
include(":feature:cart")
include(":feature:orders")
include(":feature:wishlist") 