import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.konan.properties.Properties
import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
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

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
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
        val desktopMain by getting

        androidMain.dependencies {
            // Android platform-specific library dependencies (if any)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.materialIconsExtended)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.components.uiToolingPreview)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.androidx.navigation3.ui)
            implementation(libs.landscapist.coil)
            implementation(libs.landscapist.animation)
            implementation(libs.androidx.lifecycle)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.compose.material3.windowSizeClass)
            implementation(libs.compose.material3.adaptive.navigation)
            implementation(libs.compose.material3.adaptive)
            implementation(libs.compose.material3.adaptive.layout)
            implementation(libs.compose.material3.adaptive.navigation.suite)
            // Koin dependencies
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.coroutines)
            implementation(libs.kotlinx.datetime)
        }
        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

extensions.configure<LibraryExtension> {
    namespace = "piashcse.kmp.movie"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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