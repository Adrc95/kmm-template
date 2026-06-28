import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.com.google.ksp)
}

kotlin {
    android {
        compileSdk = BuildVersion.android.compileSdk
        namespace = "${BuildVersion.environment.applicationId}.presentation.ui"
        minSdk = BuildVersion.android.minSdk

        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(BuildVersion.environment.jvmTarget)
        }

        androidResources {
            enable = true
        }

        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "PresentationUi"
            isStatic = true
            binaryOption("bundleId", baseName)

        }
    }

    sourceSets {
        applyDefaultHierarchyTemplate()

        androidMain {
            dependencies {
                implementation(libs.compose.uitooling)
                implementation(libs.compose.multiplatform.ui.tooling.preview)
                implementation(libs.bundles.android.core.ui)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.ktor.client.okhttp.android)
            }
        }

        commonMain.dependencies {
            implementation(libs.compose.multiplatform.runtime)
            implementation(libs.compose.multiplatform.material3)
            implementation(libs.compose.multiplatform.material.icons)
            implementation(libs.compose.multiplatform.ui.tooling.preview)
            implementation(libs.bundles.layer.core.ui)
            implementation(libs.compose.multiplatform.components.resources)
            implementation(projects.shared.core.ui)
            implementation(projects.shared.core.common)
            implementation(projects.shared.core.di)
            implementation(projects.shared.presentation.viewmodels)
            implementation(projects.shared.domain.models)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.okhttp.ios)
        }

        iosTest.dependencies {

        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.multiplatform.ui.tooling)
}

buildConfig {
// BuildConfig configuration here.
// https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}

tasks.register("testClasses") {
    println("Dummy classes")
}
