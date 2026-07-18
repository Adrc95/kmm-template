import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.com.google.ksp)
}
kotlin {
    android {
        compileSdk = BuildVersion.android.compileSdk
        namespace = "${BuildVersion.environment.applicationId}.core.di"
        minSdk = BuildVersion.android.minSdk

        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(BuildVersion.environment.jvmTarget)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "core.di"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.core.common)
            implementation(projects.shared.core.ui)
            implementation(projects.shared.data.datasourcesCore)
            implementation(projects.shared.data.repository)
            implementation(projects.shared.domain.usecases)
            implementation(projects.shared.presentation.viewmodels)
            implementation(libs.bundles.layer.core.common)
        }

        androidMain.dependencies {
            implementation(libs.bundles.android.core)
        }

        iosMain.dependencies {

        }
    }
}
