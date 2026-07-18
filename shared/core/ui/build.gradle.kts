import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.com.google.ksp)
}
kotlin {
    android {
        compileSdk = BuildVersion.android.compileSdk
        namespace = "${BuildVersion.environment.applicationId}.core.ui"
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
            baseName = "CoreUi"
            isStatic = true
        }
    }


    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.core.common)
            implementation(libs.compose.multiplatform.runtime)
            implementation(libs.compose.multiplatform.material3)
            implementation(libs.compose.multiplatform.material.icons)
            implementation(libs.bundles.layer.core.ui)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.bundles.android.core.ui)
        }

        iosMain.dependencies {

        }
    }
}

tasks.register("testClasses") {
    println("Dummy classes")
}
