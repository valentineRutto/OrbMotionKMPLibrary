import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    `maven-publish`
    signing
}

group = "io.github.valentinerutto"
version = "0.1.0"

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

publishing {
    publications {
        withType<MavenPublication>().configureEach {
            groupId = "io.github.valentinerutto"
            artifactId = "orbmotion"
            version = "0.1.0"

            pom {
                name.set("OrbMotion")
                description.set("Kotlin Multiplatform orb animation library")
                url.set("https://github.com/valentineRutto/OrbMotionKMPLibrary")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("valentinerutto")
                        name.set("Valentine Rutto")
                        email.set("vruttoapps@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:github.com/valentineRutto/OrbMotionKMPLibrary.git")
                    developerConnection.set("scm:git:ssh://git@github.com/valentineRutto/OrbMotionKMPLibrary.git")
                    url.set("https://github.com/valentineRutto/OrbMotionKMPLibrary")
                }
            }
        }
    }

    repositories {
        mavenLocal()
        maven {
            name = "MavenCentral"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                // Use Gradle properties or environment variables for credentials
                username = project.findProperty("ossrhUsername") as String? ?: System.getenv("OSSRH_USERNAME")
                password = project.findProperty("ossrhPassword") as String? ?: System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}

val hasSigningCredentials = project.findProperty("signingKey") != null || System.getenv("GPG_PRIVATE_KEY") != null

if (hasSigningCredentials) {
    signing {
        val signingKey = project.findProperty("signingKey") as String?
            ?: System.getenv("GPG_PRIVATE_KEY")
        val signingPassword = project.findProperty("signingPassword") as String?
            ?: System.getenv("GPG_PASSWORD")

        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
}

