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
    // iOS targets
    iosArm64()
    iosSimulatorArm64()
    iosX64()

    // iOS framework configuration
    targets
        .filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>()
        .forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "OrbMotion"
                isStatic = false
            }
        }

    // Android
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
    coordinates(
        groupId = "io.github.valentinerutto",
        artifactId = "orbmotion",
        version = "1.0.0"
    )

    publishToMavenCentral()

    signAllPublications()

    pom {
        name.set("OrbMotion")

        description.set(
            "Kotlin Multiplatform orb animation library"
        )

        inceptionYear.set("2026")

        url.set(
            "https://github.com/valentineRutto/OrbMotionKMPLibrary"
        )

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("repo")
            }
        }

        developers {
            developer {
                id.set("valentinerutto")
                name.set("Valentine Rutto")
                email.set("vruttoapps@gmail.com")
                organization.set("Valentine Rutto")
                organizationUrl.set(
                    "https://github.com/valentineRutto"
                )
            }
        }

        scm {
            connection.set(
                "scm:git:git://github.com/valentineRutto/OrbMotionKMPLibrary.git"
            )

            developerConnection.set(
                "scm:git:ssh://git@github.com/valentineRutto/OrbMotionKMPLibrary.git"
            )

            url.set(
                "https://github.com/valentineRutto/OrbMotionKMPLibrary"
            )
        }
    }
}