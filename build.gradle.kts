// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
}
dependencies {
    detektPlugins(libs.detekt.formatting)
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom(file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    parallel = true
    allRules = false
}

true // Needed to make the Suppress annotation work for the plugins block
