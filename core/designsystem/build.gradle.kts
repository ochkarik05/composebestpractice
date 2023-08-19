@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("shineapp.android.library")
    id("shineapp.android.compose")
}

android {
    namespace = "com.chisw.designsystem"

    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {

    implementation(project(":core:data"))

    api(libs.activity.compose)
    api(libs.appcompat)
    api(libs.core.ktx)
    api(libs.lifecycle.runtime.ktx)
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling.preview)
    api(libs.androidx.navigation.compose)
    api(platform(libs.compose.bom))
    api(libs.material3)
    api(libs.androidx.compose.material.iconsExtended)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.agent)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(platform(libs.compose.bom))

    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.ui.tooling)

    testImplementation(libs.junit)
}
