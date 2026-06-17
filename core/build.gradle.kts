/*
 *     Gaugometer/Gaugometer.core
 *     build.gradle.kts Copyrighted by Yamin Siahmargooei at 2026/6/17
 *     build.gradle.kts Last modified at 2026/6/17
 *     This file is part of Gaugometer/Gaugometer.core.
 *     Copyright (C) 2026  Yamin Siahmargooei
 *
 *     Gaugometer/Gaugometer.core is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Gaugometer/Gaugometer.core is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Gaugometer.  If not, see <https://www.gnu.org/licenses/>.
 */

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.compose.plugin)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.hilt)
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
        buildConfig = true
    }
}

dependencies {
    //core android/kotlin
    api(libs.androidx.core.ktx)
    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.lifecycle.compose)
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