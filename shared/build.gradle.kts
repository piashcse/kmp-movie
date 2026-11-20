import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.build.config)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    android {
        // Must differ from :androidApp's namespace; AGP 9 enforces unique package names.
        namespace = "piashcse.kmp.movie.shared"
        compileSdk {
            version = release(libs.versions.android.compile.sdk.get().toInt()) {
                minorApiLevel = libs.versions.android.compile.minor.get().toInt()
            }
        }
        minSdk = libs.versions.android.min.sdk.get().toInt()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
        // Compose Multiplatform resources are packaged as Android assets. The Android-KMP
        // plugin disables Android resource/asset processing by default, so opt in.
        androidResources {
            enable = true
        }
        // Compose Preview needs the compiled Android resources on a host-test component.
        withHostTest {
            isIncludeAndroidResources = true
        }
    }

    jvm("desktop")

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
            // Android platform-specific library dependencies (if any)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.backhandler)
            implementation(libs.compose.material.icons.extended)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.components.ui.tooling.preview)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.androidx.navigation3.ui)
            implementation(libs.landscapist.coil)
            implementation(libs.landscapist.animation)
            implementation(libs.androidx.lifecycle)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.compose.material3.window.size)
            implementation(libs.compose.material3.adaptive.navigation)
            implementation(libs.compose.material3.adaptive)
            implementation(libs.compose.material3.adaptive.layout)
            implementation(libs.compose.material3.adaptive.navigation.suite)
            // Koin dependencies
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.multiplatform.settings.no.arg)
            implementation(libs.multiplatform.settings.coroutines)
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
        getByName("desktopMain").dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

compose.resources {
    packageOfResClass = "kmp_movie.composeapp.generated.resources"
}

buildConfig {
    packageName("kmp_movie.composeApp")
    val apiKey = loadLocalProperty("local.properties", "API_KEY")
    buildConfigField("API_KEY", apiKey)
}

fun Project.loadLocalProperty(
    path: String,
    propertyName: String,
): String {
    val localProperties = Properties()
    val localPropertiesFile = project.rootProject.file(path)
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
        return localProperties.getProperty(propertyName)
    } else {
        throw GradleException("can not find property : $propertyName")
    }
}
