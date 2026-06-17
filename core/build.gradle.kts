plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "io.github.yamin8000.gaugometer.core"
    compileSdk = 37

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    //core android/kotlin
    api(libs.androidx.core.ktx)
    //compose
    api(libs.androidx.ui)
    api(libs.androidx.material)
    api(libs.androidx.ui.tooling.preview)
    debugApi(libs.androidx.ui.tooling)
    api(libs.androidx.activity.compose)
    api(libs.androidx.material.icons.extended)
    api(libs.androidx.material3)
    api(libs.androidx.material3.window.size)
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.androidx.lifecycle.viewmodel.compose)
}