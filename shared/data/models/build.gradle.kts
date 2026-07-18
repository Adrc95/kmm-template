import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.com.google.ksp)
    alias(libs.plugins.room)
}

kotlin {
    android {
        compileSdk = BuildVersion.android.compileSdk
        namespace = "${BuildVersion.environment.applicationId}.data.models"
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
            baseName = "DataModels"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.core.common)
            implementation(libs.bundles.layer.core.common)
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
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

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)

    configurations.all {
        exclude(group = "com.intellij", module = "annotations")
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

buildConfig {
// BuildConfig configuration here.
// https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}

tasks.register("testClasses") {
    println("Dummy classes")
}
