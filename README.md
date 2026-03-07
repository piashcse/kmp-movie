# KMP Movie (Compose Multiplatform)

[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-v1.10.2-green)](https://developer.android.com/jetpack/compose)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.10-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![ktorCleint](https://img.shields.io/badge/ktor_client-3.4.1-pink)](https://ktor.io/docs/welcome.html)
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
- 📺 Airing Today, On The Air, Popular & Upcoming TV series sections
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
- 🧭 **Bottom Navigation**
- 🚦 **Navigation Rail**
- 📄 **Pagination** supports for all platforms 

## Architecture

- **MVVM Architecture (Model - ComposableView - ViewModel)**

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
#### ⚠️ If you haven’t generated an API key yet, feel free to use the demo key below for development use only.

```kotlin
API_KEY=59cd6896d8432f9c69aed9b86b9c2931
```

## Built With 🛠

- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) - Compose
  Multiplatform, a modern UI framework for Kotlin that makes building performant and beautiful user
  interfaces.
- [Navigation 3](https://developer.android.com/guide/navigation/navigation-3) - Compose Multiplatform Navigation backed by JetBrains
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
- [XCode](https://developer.apple.com/xcode/) - Xcode 14 includes everything you need to develop,
  test, and distribute apps across all Apple platforms.

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
- Or
  use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
  for Android Studio
  Run iOS simulator UI tests: `./gradlew :composeApp:iosSimulatorArm64Test`

### Desktop

- Run the desktop application: `./gradlew :composeApp:run`
- Run desktop UI tests: `./gradlew :composeApp:jvmTest`

### Web

- Before running make sure you have `yarn 1.22.22`
- Run the web application: `./gradlew wasmJsBrowserDevelopmentRun`

## Project structure

### [`composeApp`](/composeApp)

This is a Kotlin module that contains the logic common for all platforms.
This shared module is where you write your Compose Multiplatform code. In
`composeApp/src/commonMain/kotlin/App.kt`, you can find the shared root `@Composable` function for
your app.
It uses Gradle as the build system. You can add dependencies and change settings in
`composeApp/build.gradle.kts`. The shared module builds into platform-specific libraries:
- Android library for Android
- Framework for iOS
- JVM library for Desktop
- JavaScript library for Web

#### Complete `composeApp` directory structure

The complete structure of the `composeApp` module showing all platform-specific directories:

```
composeApp/
├── build.gradle.kts
├── src/
│   ├── androidMain/
│   │   ├── kotlin/
│   │   ├── res/
│   │   │   ├── drawable/
│   │   │   ├── drawable-v24/
│   │   │   ├── mipmap-anydpi-v26/
│   │   │   ├── mipmap-hdpi/
│   │   │   ├── mipmap-mdpi/
│   │   │   ├── mipmap-xhdpi/
│   │   │   ├── mipmap-xxhdpi/
│   │   │   ├── mipmap-xxxhdpi/
│   │   │   └── values/
│   │   └── AndroidManifest.xml
│   ├── commonMain/
│   │   ├── composeResources/
│   │   │   ├── drawable/
│   │   │   └── values/
│   │   └── kotlin/
│   │       ├── constant/
│   │       │   └── AppConstant.kt
│   │       ├── data/
│   │       │   ├── model/
│   │       │   │   ├── artist/
│   │       │   │   ├── celebrities/
│   │       │   │   ├── movie_detail/
│   │       │   │   ├── tv_detail/
│   │       │   │   ├── BaseModel.kt
│   │       │   │   ├── MovieItem.kt
│   │       │   │   └── TvSeriesItem.kt
│   │       │   ├── remote/
│   │       │   │   ├── ApiClient.kt
│   │       │   │   ├── ApiInterface.kt
│   │       │   │   └── ApiService.kt
│   │       │   └── repository/
│   │       │       └── Repository.kt
│   │       ├── di/
│   │       │   ├── AppModule.kt
│   │       │   ├── KoinApplication.kt
│   │       │   ├── KoinInitializer.kt
│   │       │   └── PlatformModule.kt
│   │       ├── navigation/
│   │       │   ├── NavGraph.kt
│   │       │   └── NavigationScreen.kt
│   │       ├── theme/
│   │       │   ├── Color.kt
│   │       │   ├── Shape.kt
│   │       │   ├── Theme.kt
│   │       │   └── Type.kt
│   │       ├── ui/
│   │       │   ├── component/
│   │       │   ├── screens/
│   │       │   └── App.kt
│   │       └── utils/
│   │           ├── network/
│   │           ├── CommonExtension.kt
│   │           ├── Platform.kt
│   │           ├── UIExtension.kt
│   │           └── Utils.kt
│   ├── desktopMain/
│   │   └── kotlin/
│   ├── iosMain/
│   │   └── kotlin/
│   └── wasmJsMain/
│       ├── kotlin/
│       └── resources/
```

### [`androidApp`](/composeApp/src/androidMain/)

This is a Kotlin module that builds into an Android application. It uses Gradle as the build system.
The `androidApp` module depends on and uses the shared module as a regular Android library.

### [`iosApp`](/composeApp/src/iosMain/)

This is an Xcode project that builds into an iOS application. It depends on and uses the shared
module as a regular iOS framework.

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
