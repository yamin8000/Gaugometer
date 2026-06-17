/*
 *     Gaugometer/Gaugometer.app
 *     build.gradle.kts Copyrighted by Yamin Siahmargooei at 2024/8/11
 *     build.gradle.kts Last modified at 2024/8/11
 *     This file is part of Gaugometer/Gaugometer.app.
 *     Copyright (C) 2024  Yamin Siahmargooei
 *
 *     Gaugometer/Gaugometer.app is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Gaugometer/Gaugometer.app is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Gaugometer.  If not, see <https://www.gnu.org/licenses/>.
 */

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.compose.plugin)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "io.github.yamin8000.gaugometer"
    compileSdk = 37

    defaultConfig {
        applicationId = "io.github.yamin8000.gaugometer"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0.0"
        base.archivesName = "$applicationId-v$versionCode-n$versionName"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isMinifyEnabled = true
            isShrinkResources = true
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/en/*"
            excludes += "/*.yml"
        }
    }
}

dependencies {
    //core
    implementation(project(":core"))
    implementation(project(":gauge"))
    implementation(libs.androidx.core.splashscreen)
    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.lifecycle.compose)
    //navigation
    implementation(libs.androidx.navigation.compose)
}