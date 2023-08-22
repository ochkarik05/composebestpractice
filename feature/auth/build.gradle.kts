@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("shineapp.android.library")
    id("shineapp.android.compose")
}

android {
    namespace = "com.chisw.auth"

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
    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            ),
        )
    }
}

dependencies {

    implementation(project(":core:auth"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(platform(libs.compose.bom))

    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.ui.tooling)

    testImplementation(libs.junit)
}
