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
        namespace = "${BuildVersion.environment.applicationId}.core.common"
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
            baseName = "CoreCommon"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.layer.core.common)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.bundles.android.core)

        }

        iosMain.dependencies {

        }
    }
}

tasks.register("testClasses") {
    println("Dummy classes")
}
