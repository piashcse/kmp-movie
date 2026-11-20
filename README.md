# KMP Movie (Compose Multiplatform)

[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-v1.12.2-green)](https://www.jetbrains.com/compose-multiplatform/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.21-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![ktorClient](https://img.shields.io/badge/ktor_client-3.6.1-pink)](https://ktor.io/docs/welcome.html)
[![Gradle](https://img.shields.io/badge/Gradle-9.9.2-02303A?logo=gradle&logoColor=white)](https://gradle.org)
[![AGP](https://img.shields.io/badge/AGP-9.5.1-3DDC84?logo=android&logoColor=white)](https://developer.android.com/build)
![badge-Android](https://img.shields.io/badge/Platform-Android-brightgreen)
![badge-iOS](https://img.shields.io/badge/Platform-iOS-lightgray)
![badge-desktop](http://img.shields.io/badge/Platform-Desktop-4D76CD.svg?style=flat)
![badge-web](https://img.shields.io/badge/Platform-Web-blueviolet.svg?style=flat)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
<a href="https://github.com/piashcse"><img alt="License" src="https://img.shields.io/static/v1?label=GitHub&message=piashcse&color=C51162"/></a>

KMP Movie app built with Compose Multiplatform, supporting Android, iOS, Desktop, and Web. The app
follows the MVVM architecture to ensure clean, maintainable code and delivers a responsive, modern
UI experience across all platforms, leveraging [The Movie DB API](https://www.themoviedb.org). </br>

# Platform

- iOS
- Android
- Desktop
- Web

<p float="center">
  <img width="100%" height="50%" src="https://github.com/piashcse/kmm-movie/blob/master/screenshots/Screenshot 2024-11-06-movie.png"/> </br></br>
  <img width="100%" height="50%" src="https://github.com/piashcse/kmm-movie/blob/master/screenshots/Screenshot 2024-11-06-detail.png" /></br></br>
  <img width="100%" height="50%" src="https://github.com/piashcse/kmm-movie/blob/master/screenshots/Screen Recording 2024-11-06.gif" />
</p>

# Main Features

### Movies
- 🎞 Now Playing, Popular, Top Rated & Upcoming movie sections
- 🔍 Movie Detail Pages with Cast & Crew
- 🎯 Recommended Movies
- 🔍 Search Movies
- 👤 Artist/Actor Detail Page with navigation from movie cast

### TV Series
- 📺 Airing Today, On The Air, Popular & Top Rated TV series sections
- 🔍 TV Series Detail Pages with Cast & Crew
- 🎯 Recommended TV Series
- 🔍 Search TV Series
- 👤 Artist/Actor Detail Page with navigation from TV series cast

### Celebrity
- 🌟 Popular and Trending Celebrities/Persons sections
- 🔍 Celebrity Search functionality
- 👤 Celebrity Detail Page with navigation from movie/tv cast

### Favorites
- ❤️ Add Movies, TV Series, and Celebrities to your favorites list
- 💾 Persistent storage of favorite items across sessions

### Common Features
- 🧭 **Adaptive navigation** — bottom bar, navigation rail and drawer
- 🌓 **Theme mode** — light, dark or system, persisted across sessions
- 🏷️ **Genre filtering** on every movie and TV list
- ❤️ **Reactive favorites** shared between all screens
- 🔎 **Debounced search** with movie, TV and celebrity results
- 📄 **Pagination** on all platforms

## Architecture

- **MVVM Architecture (Model - ComposableView - ViewModel)**
- ViewModels expose immutable state through `StateFlow`; screens are plain composables.
- Koin provides dependency injection (`di/AppModule.kt`), repositories are the single
  source of truth for data, and favorites/preferences are persisted with Multiplatform Settings.

<p float="left">
  <img width="100%" height="60%" src="https://github.com/piashcse/kmm-movie/blob/master/screenshots/mvvm_architecture.png" />
</p>

## API Key 🔑

You will need to provide a developer key to fetch the data from TMDB API.

* Generate a new key (v3 auth) from [here](https://www.themoviedb.org/settings/api). Copy the key
  and go back to the project.
* Add a new entry in `local.properties` file:

```kotlin
API_KEY=superSecretSampleApiKeyOf32Chars
```

> `local.properties` is git-ignored — never commit a real API key.

## Built With 🛠

- [Kotlin](https://kotlinlang.org) - A modern, concise programming language for multiplatform development.
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) - Compose
  Multiplatform, a modern UI framework for Kotlin that makes building performant and beautiful user
  interfaces.
- [Gradle](https://gradle.org) - Build tool; dependency versions are centralized in
  `gradle/libs.versions.toml`.
- [Android Gradle Plugin](https://developer.android.com/build) - Android build integration, including
  built-in Kotlin and the Android-KMP library plugin.
- [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings) - A consistent, platform-agnostic key-value storage for Kotlin Multiplatform.
- [kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime) - A multiplatform date and time library for Kotlin.
- [Ktor Client](https://ktor.io/docs/welcome.html) - Ktor includes a multiplatform asynchronous HTTP
  client, which allows you to make requests and handle responses.
- [Koin](https://insert-koin.io/) - A pragmatic lightweight dependency injection framework for Kotlin
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) - Kotlin multiplatform /
  multi-format reflectionless serialization
- [View Model](https://developer.android.com/topic/libraries/architecture/viewmodel) - The ViewModel
  class is a business logic or screen level state holder. It exposes state to the UI and
  encapsulates related business logic
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous
  and more.
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) -
  A cold asynchronous data stream that sequentially emits values and completes normally or with an
  exception.
- [Landscapist](https://github.com/skydoves/landscapist) - 🌻 A pluggable, highly optimized Jetpack
  Compose and Kotlin Multiplatform image loading library that fetches and displays network images
  with Glide, Coil, and Fresco.
- [Android Studio](https://developer.android.com/studio/intro) - Android Studio is the official
  Integrated Development Environment (IDE) for Android app development.
- [Xcode](https://developer.apple.com/xcode/) - The IDE for building, testing and distributing the
  iOS application.

## Before running!

- check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
- install **JDK 17** (required by AGP 9 and the JVM 17 compilation target)
- install **Android SDK Platform 37.1** (`compileSdk`) through the SDK Manager
- add a `local.properties` file to the project root with your SDK path and API key:
  `sdk.dir=/path/to/Android/sdk` and `API_KEY=...` (see above)
- install **Node.js** with **Yarn 1.22.22** for the web target

### Android

To run the application on Android device/emulator:

- open the project in Android Studio and run the imported android run configuration

To build the debug APK:

- run `./gradlew :androidApp:assembleDebug`
- find the `.apk` in `androidApp/build/outputs/apk/debug/androidApp-debug.apk`
- run Android host tests: `./gradlew :shared:testAndroidHostTest`

### iOS

To run the application on an iPhone device/simulator:

- Open `iosApp/iosApp.xcodeproj` in Xcode and run a standard configuration
- Or use the [Kotlin Multiplatform plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
  for Android Studio
- run iOS simulator tests: `./gradlew :shared:iosSimulatorArm64Test`

### Desktop

- Run the desktop application: `./gradlew :desktopApp:run`
- Run with Compose Hot Reload: `./gradlew :desktopApp:hotRun`
- Run desktop tests: `./gradlew :shared:desktopTest`

### Web

- Before running make sure you have `yarn 1.22.22`
- Run the web application: `./gradlew :webApp:wasmJsBrowserDevelopmentRun`

## Project structure

```
kmp-movie/
├── shared/            # Kotlin Multiplatform module (UI, data, DI)
│   └── src/
│       ├── commonMain/   # Compose UI, ViewModels, repositories, Ktor client
│       ├── androidMain/  # Android-specific implementations
│       ├── iosMain/      # iOS implementations and MainViewController
│       ├── desktopMain/  # Desktop/JVM implementations
│       └── webMain/      # Wasm implementations
├── androidApp/        # Android application module (manifest + resources)
├── desktopApp/        # Compose Desktop application
├── webApp/            # Wasm browser host
├── iosApp/            # Xcode project consuming the shared framework
└── gradle/
    └── libs.versions.toml   # central dependency and plugin versions
```

### [`shared`](/shared)

The multiplatform module containing the Compose UI, ViewModels, repositories, networking and
dependency injection. It builds into an Android library, an iOS framework, a JVM library for
Desktop and a Wasm library for the web.

### [`androidApp`](/androidApp)

The Android application module. It depends on `shared` and owns the Android manifest, launcher
resources and the Activity entry point.

### [`iosApp`](/iosApp)

The Xcode project that hosts the Compose UI through the framework produced by `shared`.

### [`desktopApp`](/desktopApp)

The Compose Desktop application; supports running with Compose Hot Reload.

### [`webApp`](/webApp)

The Wasm browser host for the shared Compose UI.

## Testing

- Desktop: `./gradlew :shared:desktopTest`
- Android host tests: `./gradlew :shared:testAndroidHostTest`
- iOS simulator: `./gradlew :shared:iosSimulatorArm64Test`
- Wasm: `./gradlew :webApp:wasmJsTest`

## Acknowledgements

- [Kotlin Multiplatform Wizard](https://kmp.jetbrains.com/) For Starter template

## 👨 Developed By

<a href="https://twitter.com/piashcse" target="_blank">
  <img src="https://avatars.githubusercontent.com/piashcse" width="90" align="left">
</a>

**Mehedi Hassan Piash**

[![Twitter](https://img.shields.io/badge/-Twitter-1DA1F2?logo=x&logoColor=white&style=for-the-badge)](https://twitter.com/piashcse)
[![Medium](https://img.shields.io/badge/-Medium-00AB6C?logo=medium&logoColor=white&style=for-the-badge)](https://medium.com/@piashcse)
[![Linkedin](https://img.shields.io/badge/-LinkedIn-0077B5?logo=linkedin&logoColor=white&style=for-the-badge)](https://www.linkedin.com/in/piashcse/)
[![Web](https://img.shields.io/badge/-Web-0073E6?logo=appveyor&logoColor=white&style=for-the-badge)](https://piashcse.github.io/)
[![Blog](https://img.shields.io/badge/-Blog-0077B5?logo=readme&logoColor=white&style=for-the-badge)](https://piashcse.blogspot.com)

# License

```
Copyright 2024-2026 piashcse (Mehedi Hassan Piash)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
