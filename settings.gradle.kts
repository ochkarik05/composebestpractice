@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
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
include(":core:auth")
include(":core:common")
include(":core:data")
include(":core:designsystem")
include(":core:domain")
include(":core:ui")
include(":feature:animation")
include(":feature:auth")
include(":feature:layouts")
include(":feature:main")
include(":feature:savingstate")
