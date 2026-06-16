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
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "io.github.yamin8000.gaugometer"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.yamin8000.gaugometer"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
        base.archivesName = "$applicationId-v$versionCode-n$versionName"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        languageVersion = "1.9"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kotlin.sourceSets.configureEach {
        languageSettings.enableLanguageFeature("DataObjects")
    }
}

dependencies {
    //core
    implementation(project(":core"))
    implementation("androidx.core:core-splashscreen:1.0.1")
    //navigation
    val navVersion = "2.8.1"
    implementation("androidx.navigation:navigation-compose:$navVersion")
    //gauge
    implementation("com.github.yamin8000.gauge:Gauge:1.0.4")
    //accompanist
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")
}