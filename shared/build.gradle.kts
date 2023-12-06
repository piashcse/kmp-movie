plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()
    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
         commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.animation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                api(compose.materialIconsExtended)

                implementation("io.ktor:ktor-client-core:2.3.6")
                implementation("io.ktor:ktor-client-logging:2.3.6")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.6")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                api("io.github.qdsfdhvh:image-loader:1.7.1")
                api("moe.tlaster:precompose:1.5.8")
                api("moe.tlaster:precompose-viewmodel:1.5.8")
            }
        }
        androidMain {
            dependencies {
                api("androidx.activity:activity-compose:1.8.1")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.12.0")
                implementation("io.ktor:ktor-client-okhttp:2.3.5")
            }
        }
        iosMain{
            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.3.6")
                implementation("io.ktor:ktor-client-ios:2.3.6")
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation("io.ktor:ktor-client-cio:2.3.6")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.kmm.movie.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
