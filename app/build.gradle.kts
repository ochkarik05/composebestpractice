@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("shineapp.android.application")
    id("shineapp.android.hilt")
    id("shineapp.android.app.compose")
}

android {
    namespace = "com.chisw.composesample"

    defaultConfig {
        applicationId = "com.chisw.composesample"
        versionCode = 3
        versionName = "0.0.$versionCode"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packaging {
        resources.excludes += setOf(
            // Exclude AndroidX version files
            "META-INF/*.version",
            // Exclude consumer proguard files
            "META-INF/proguard/*",
            // Exclude the Firebase/Fabric/other random properties files
            "/*.properties",
            "fabric/*.properties",
            "META-INF/*.properties",
        )
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(project(":core:auth"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:common"))
    implementation(project(":feature:animation"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:layouts"))
    implementation(project(":feature:main"))
    implementation(project(":feature:savingstate"))

    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.ui.test.junit4)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
