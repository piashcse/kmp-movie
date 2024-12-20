# KMP Movie (Compose Multiplatform)  
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-v1.7.3-green)](https://developer.android.com/jetpack/compose)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![ktorCleint](https://img.shields.io/badge/ktorclient-3.0.3-pink)](https://ktor.io/docs/welcome.html)
![badge-Android](https://img.shields.io/badge/Platform-Android-brightgreen)
![badge-iOS](https://img.shields.io/badge/Platform-iOS-lightgray)
![badge-desktop](http://img.shields.io/badge/Platform-Desktop-4D76CD.svg?style=flat)
![badge-web](https://img.shields.io/badge/Platform-Web-blueviolet.svg?style=flat)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
<a href="https://github.com/piashcse"><img alt="License" src="https://img.shields.io/static/v1?label=GitHub&message=piashcse&color=C51162"/></a>

KMP Movie app built with Compose Multiplatform, supporting Android, iOS, Desktop, and Web. The app follows the MVVM architecture to ensure clean, maintainable code and delivers a responsive, modern UI experience across all platforms, leveraging [The Movie DB API](https://www.themoviedb.org). </br>

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
- Movie
  - Movie List  
  - Movie Search
  - Movie Detail
  - Recommended Movie
- TV Series
  - TV Series List
  - TV Series Search
  - TV Series Detail
  - Recommended TV Series 
- Artist Detail
- Bottom Navigation
- Navigation Rail

## Architecture
  - MVVM Architecture (Model - ComposableView - ViewModel)

<p float="left">
  <img width="100%" height="60%" src="https://github.com/piashcse/kmm-movie/blob/master/screenshots/mvvm_architecture.png" />
</p>

## API Key 🔑
You will need to provide a developer key to fetch the data from TMDB API.
* Generate a new key (v3 auth) from [here](https://www.themoviedb.org/settings/api). Copy the key and go back to the project.
* Add the key to build config in `./composeApp/build.gradle.kts`:

```kotlin
defaultConfig {
    ...
    buildConfigField("API_KEY", TMDB_API_KEY)
    ...
}
```

## Built With 🛠
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) - Compose Multiplatform, a modern UI framework for Kotlin that makes building performant and beautiful user interfaces.
- [PreCompose](https://github.com/Tlaster/PreCompose) - Compose Multiplatform Navigation && State Management
- [Ktor Client](https://ktor.io/docs/welcome.html) - Ktor includes a multiplatform asynchronous HTTP client, which allows you to make requests and handle responses.
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) - Kotlin multiplatform / multi-format reflectionless serialization
- [View Model](https://developer.android.com/topic/libraries/architecture/viewmodel) - The ViewModel class is a business logic or screen level state holder. It exposes state to the UI and encapsulates related business logic
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more.
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Landscapist](https://github.com/skydoves/landscapist) - 🌻 A pluggable, highly optimized Jetpack Compose and Kotlin Multiplatform image loading library that fetches and displays network images with Glide, Coil, and Fresco.
- [Android Studio](https://developer.android.com/studio/intro) - Android Studio is the official Integrated Development Environment (IDE) for Android app development.
- [XCode](https://developer.apple.com/xcode/) - Xcode 14 includes everything you need to develop, test, and distribute apps across all Apple platforms.

## Before running!
 - check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
 - install JDK 11 or higher on your machine
 - add `local.properties` file to the project root and set a path to Android SDK there

### Android
To run the application on Android device/emulator:  
 - open the project in Android Studio and run the imported android run configuration

To build the application bundle:
 - run `./gradlew :composeApp:assembleDebug`
 - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`
Run android simulator UI tests: `./gradlew :composeApp:pixel5Check`

### iOS
To run the application on an iPhone device/simulator:
 - Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
 - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio
Run iOS simulator UI tests: `./gradlew :composeApp:iosSimulatorArm64Test`

### Desktop
- Run the desktop application: `./gradlew :composeApp:run`
- Run desktop UI tests: `./gradlew :composeApp:jvmTest`

### Web
- Before running make sure you have `yarn 1.22.22`
- Run the web application: `./gradlew wasmJsBrowserRun`

## Project structure 

This Compose Multiplatform project includes three modules:

### [`composeApp`](/composeApp)
This is a Kotlin module that contains the logic common for both Android and iOS applications, the code you share between platforms.
This shared module is also where you write your Compose Multiplatform code. In `composeApp/src/commonMain/kotlin/App.kt`, you can find the shared root `@Composable` function for your app.
It uses Gradle as the build system. You can add dependencies and change settings in `shared/build.gradle.kts`. The shared module builds into an Android library and an iOS framework.

### [`androidApp`](/composeApp/src/androidMain/)
This is a Kotlin module that builds into an Android application. It uses Gradle as the build system. The `androidApp` module depends on and uses the shared module as a regular Android library.

### [`iosApp`](/iosApp)
This is an Xcode project that builds into an iOS application. It depends on and uses the shared module as a CocoaPods dependency.

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
Copyright 2024 piashcse (Mehedi Hassan Piash)

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
