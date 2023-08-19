@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("shineapp.android.library")
    id("shineapp.android.kotlin.inject")
}

android {
    namespace = "com.chisw.domain"

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
    implementation(project(":core:data"))
    implementation(project(":core:common"))

    implementation(libs.appcompat)
    implementation(libs.core.ktx)
    implementation(libs.material)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.truth)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
