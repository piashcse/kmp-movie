# Movie World (Compose Multiplatform)  
[![Compose Multiplatform](https://img.shields.io/github/v/release/JetBrains/compose-multiplatform?color=brightgreen&label=Compose%20Multiplatform)](https://github.com/JetBrains/compose-multiplatform/releases/latest)
![badge-Android](https://img.shields.io/badge/Platform-Android-brightgreen)
![badge-iOS](https://img.shields.io/badge/Platform-iOS-lightgray)
![badge-desktop](http://img.shields.io/badge/Platform-Desktop-4D76CD.svg?style=flat)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.21-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
<a href="https://github.com/piashcse"><img alt="License" src="https://img.shields.io/static/v1?label=GitHub&message=piashcse&color=C51162"/></a>

Movie World app using [The Movie DB](https://www.themoviedb.org) built with Compose Multiplatform and MVVM architecture.<br>

# Platform
- iOS
- Android
- Desktop
<p float="left">
  <img width="30%" height="60%" src="https://github.com/piashcse/kmm-movie/blob/master/screenshots/1699203945755_100.PNG" />
  <img width="30%" height="60%" src="https://github.com/piashcse/kmm-movie/blob/master/screenshots/1699205418921_100.PNG" />
  <img width="30%" height="60%" src="https://github.com/piashcse/kmm-movie/blob/master/screenshots/1699203963683_100.PNG" />
</p>


# Main Features
- Movie List 
- Movie Search
- Movie Detail
- Navigation 
- Bottom Navigation

## Architecture
  - MVVM Architecture (Model - ComposableView - ViewModel)

## Built With 🛠
- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) - Compose Multiplatform, a modern UI framework for Kotlin that makes building performant and beautiful user interfaces.
- [PreCompose](https://github.com/Tlaster/PreCompose) - Compose Multiplatform Navigation && State Management
- [Ktor Client](https://ktor.io/docs/welcome.html) - Ktor includes a multiplatform asynchronous HTTP client, which allows you to make requests and handle responses.
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) - Kotlin multiplatform / multi-format reflectionless serialization
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more.
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Image Loader](https://github.com/qdsfdhvh/compose-imageloader) - Compose Image library for Kotlin Multiplatform.
- [Android Studio](https://developer.android.com/studio/intro) - Android Studio is the official Integrated Development Environment (IDE) for Android app development.
- [XCode](https://developer.apple.com/xcode/) - Xcode 14 includes everything you need to develop, test, and distribute apps across all Apple platforms.

## Project structure 

This Compose Multiplatform project includes three modules:

### [`shared`](/shared)
This is a Kotlin module that contains the logic common for both Android and iOS applications, the code you share between platforms.
This shared module is also where you write your Compose Multiplatform code. In `shared/src/commonMain/kotlin/App.kt`, you can find the shared root `@Composable` function for your app.
It uses Gradle as the build system. You can add dependencies and change settings in `shared/build.gradle.kts`. The shared module builds into an Android library and an iOS framework.

### [`androidApp`](/androidApp)
This is a Kotlin module that builds into an Android application. It uses Gradle as the build system. The `androidApp` module depends on and uses the shared module as a regular Android library.

### [`iosApp`](/iosApp)
This is an Xcode project that builds into an iOS application. It depends on and uses the shared module as a CocoaPods dependency.

## Acknowledgements 

- [JetBrains/compose-multiplatform-ios-android-template](https://github.com/JetBrains/compose-multiplatform-ios-android-template#readme):
  For Starter template

## 👨 Developed By

<a href="https://twitter.com/piashcse" target="_blank">
  <img src="https://avatars.githubusercontent.com/piashcse" width="80" align="left">
</a>

**Mehedi Hassan Piash**

[![Twitter](https://img.shields.io/badge/-twitter-grey?logo=twitter)](https://twitter.com/piashcse)
[![Web](https://img.shields.io/badge/-web-grey?logo=appveyor)](https://piashcse.github.io/)
[![Medium](https://img.shields.io/badge/-medium-grey?logo=medium)](https://medium.com/@piashcse)
[![Linkedin](https://img.shields.io/badge/-linkedin-grey?logo=linkedin)](https://www.linkedin.com/in/piashcse/)

# License
```
Copyright 2023 piashcse (Mehedi Hassan Piash)

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
