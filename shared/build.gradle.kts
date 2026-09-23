import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.vanniktechMavenPublish)
}

group = "io.github.valentinerutto"
version = "1.0.0"

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            // Use a dynamic framework for CocoaPods compatibility
            isStatic = false
        }
    }

    // Note: CocoaPods configuration was removed temporarily because
    // the Kotlin Multiplatform Gradle configuration failed to locate
    // the `cocoapods` DSL during Android builds. Re-add a guarded
    // `cocoapods {}` block when using a Kotlin Gradle plugin that
    // exposes the extension (or apply the appropriate plugin).
    
    android {
       namespace = "com.valentinerutto.orbmotion.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
       withDeviceTestBuilder {
           sourceSetTreeName = "test"
       }.configure {
           instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.compose.animation)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}

mavenPublishing {
    // Publishes KMP metadata and Android/iOS variants with one root coordinate:
    // io.github.valentinerutto:orbmotion:<version>.
    publishToMavenCentral()
    signAllPublications()
    coordinates(group.toString(), "orbmotion", version.toString())

    pom {
        name.set("OrbMotion")
        description.set("Kotlin Multiplatform orb animation library")
        inceptionYear.set("2026")
        url.set("https://github.com/valentineRutto/OrbMotionKMPLibrary")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("valentinerutto")
                name.set("Valentine Rutto")
                email.set("vruttoapps@gmail.com")
                organization.set("Valentine Rutto")
                organizationUrl.set("https://github.com/valentineRutto")
            }
        }

        scm {
            connection.set("scm:git:github.com/valentineRutto/OrbMotionKMPLibrary.git")
            developerConnection.set("scm:git:ssh://git@github.com/valentineRutto/OrbMotionKMPLibrary.git")
            url.set("https://github.com/valentineRutto/OrbMotionKMPLibrary")
        }
    }
}
