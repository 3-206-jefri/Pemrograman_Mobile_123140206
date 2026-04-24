    import org.jetbrains.compose.desktop.application.dsl.TargetFormat
    import org.jetbrains.kotlin.gradle.dsl.JvmTarget

    plugins {
        alias(libs.plugins.kotlinMultiplatform)
        alias(libs.plugins.androidApplication)
        alias(libs.plugins.composeMultiplatform)
        alias(libs.plugins.composeCompiler)
        kotlin("plugin.serialization") version "1.9.0"
    }

    kotlin {
        val ktorVersion = "3.1.3"
        androidTarget {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }

        listOf(
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "ComposeApp"
                isStatic = true
            }
        }

        sourceSets {
            androidMain.dependencies {
                implementation(libs.compose.uiToolingPreview)
                implementation(libs.androidx.activity.compose)
                implementation("io.ktor:ktor-client-android:${ktorVersion}")

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
                // Ktor Client Core
                implementation("io.ktor:ktor-client-core:${ ktorVersion}")
                // Content Negotiation (untuk JSON)
                implementation("io.ktor:ktor-client-content-negotiation:${ktorVersion}")
                // Kotlinx Serialization
                implementation("io.ktor:ktor-serialization-kotlinx-json:${ktorVersion}")
                // Logging (optional)
                implementation("io.ktor:ktor-client-logging:${ktorVersion}")
                implementation("io.coil-kt.coil3:coil-compose:3.0.4")
                implementation("io.coil-kt.coil3:coil-network-ktor3:3.0.4")
                implementation("org.jetbrains.compose.material:material-icons-extended:1.6.10")
            }
            commonTest.dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }

    android {
        namespace = "com.example.tugas6"
        compileSdk = libs.versions.android.compileSdk.get().toInt()

        defaultConfig {
            applicationId = "com.example.tugas6"
            minSdk = libs.versions.android.minSdk.get().toInt()
            targetSdk = libs.versions.android.targetSdk.get().toInt()
            versionCode = 1
            versionName = "1.0"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    dependencies {
        debugImplementation(libs.compose.uiTooling)
    }

