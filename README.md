# Image Machine Apps
Nowadays, people tend to generate tons of images daily and it becomes harder and
harder to group all those images and manage them efficiently. Here comes the image
grouping application that will help ease people’s lives.

This is an Android application to help grouping images from
your photo gallery and easily access them also.

## How to run this 
- Download JDK 17 ([link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html))
- Change project setting JDK to 17
- Apply
- Rebuild project


## Built With 🛠

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android
  development.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Jetpack Compose is Android’s
  modern toolkit for building native UI
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous
  and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)
    - A cold asynchronous data stream that sequentially emits values and completes normally or with
      an exception.
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - StateFlow is a
  state-holder observable flow that emits the current and new state updates to its collectors.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) -
  Collection of libraries that help you design robust, testable, and maintainable apps.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores
      UI-related data that isn't destroyed on UI changes.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
    - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency
      injection into an Android application.
    - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) -
      DI for injecting `ViewModel`.
- [Room](https://developer.android.com/jetpack/androidx/releases/room) - an abstraction layer over
  SQLite to allow for more robust database access while harnessing the full power of SQLite.
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin
  Coroutines.
- [Material Components for Android](https://github.com/material-components/material-components-android)
    - Modular and customizable Material Design UI components for Android.

## Architecture

This app uses [***Clean
Architecture***](https://medium.com/android-dev-hacks/detailed-guide-on-android-clean-architecture-9eab262a9011)
.

![](art/clean-arch.jpeg)

## Module
- core: utility and reuse class
- data: database and repository (data logic)
- domain: business logic layer
- uikit: reuse component and theming
- app: screen and viewmodel

## Structure
- UI Layer: compose, view model
- Domain: use cases
- Data: repositories
