@file:Suppress("UnstableApiUsage")

include(":feature:auth")
include(":core:auth")
include(":feature:main")

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

rootProject.name = "Compose Sample"
include(":app")
include(":core:designsystem")
include(":core:ui")
include(":feature:savingstate")
include(":feature:animation")
include(":feature:layouts")
