import de.jensklingenberg.ktorfit.gradle.ErrorCheckingMode
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.com.google.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.room)
}

kotlin {
    android {
        compileSdk = BuildVersion.android.compileSdk
        namespace = "${BuildVersion.environment.applicationId}.data.datasources.core"
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
            baseName = "DataDatasourcesCore"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.core.common)
            implementation(projects.shared.data.models)
            implementation(projects.shared.data.datasources)

            implementation(libs.bundles.layer.data.datasources.core)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.bundles.android.data.core)
        }

        iosMain.dependencies {
            implementation(libs.bundles.ios.data.core)
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

buildConfig {
// BuildConfig configuration here.
// https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}

ktorfit {
    errorCheckingMode = ErrorCheckingMode.ERROR
    generateQualifiedTypeName = true
    compilerPluginVersion.set("2.3.5")
}

room {
    schemaDirectory("$projectDir/schemas")
}

tasks.register("testClasses") {
    println("Dummy classes")
}
