import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.HostTestBuilder

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

val releaseKeystore = providers.environmentVariable("KEYSTORE_PATH").orNull
val releaseStorePassword = providers.environmentVariable("KEYSTORE_PASSWORD").orNull
val releaseKeyAlias = providers.environmentVariable("KEY_ALIAS").orNull
val releaseKeyPassword = providers.environmentVariable("KEY_PASSWORD").orNull
val hasReleaseSigning = listOf(
    releaseKeystore,
    releaseStorePassword,
    releaseKeyAlias,
    releaseKeyPassword,
).all { !it.isNullOrBlank() }

extensions.configure<ApplicationExtension> {
    namespace = "piashcse.kmp.movie"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()
    compileSdkMinor = libs.versions.android.compile.minor.get().toInt()

    defaultConfig {
        applicationId = "piashcse.kmp.movie"
        minSdk = libs.versions.android.min.sdk.get().toInt()
        targetSdk = libs.versions.android.target.sdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        if (hasReleaseSigning) {
            create("release") {
                storeFile = file(releaseKeystore!!)
                storePassword = releaseStorePassword
                keyAlias = releaseKeyAlias
                keyPassword = releaseKeyPassword
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            if (hasReleaseSigning) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    testOptions {
        unitTests {
            // Compose Preview resolution needs compiled Android resources.
            isIncludeAndroidResources = true
        }
    }
}

// Compose Preview packages Android XML resources through the unit-test component for
// every variant; ensure it exists and includes resources.
extensions.configure<ApplicationAndroidComponentsExtension> {
    beforeVariants(selector().all()) { variant ->
        variant.hostTests[HostTestBuilder.UNIT_TEST_TYPE]?.apply {
            enable = true
            includeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.koin.android)
    debugImplementation(libs.compose.ui.tooling)
}
