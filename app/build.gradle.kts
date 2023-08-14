plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.luqman.imagemachine"
    compileSdk = AppConfig.Android.compileSdk

    defaultConfig {
        applicationId = "com.luqman.imagemachine"
        minSdk = AppConfig.Android.minSdk
        targetSdk = AppConfig.Android.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.version

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = AppConfig.Java.version
        targetCompatibility = AppConfig.Java.version
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.KotlinCompiler.version
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":uikit"))
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(AndroidDependencies.AndroidX.CoreKtx.plugin)
    implementation(AndroidDependencies.AndroidX.Lifecycle.plugin)
    implementation(AndroidDependencies.AndroidX.Compose.ViewModel.plugin)
    implementation(AndroidDependencies.AndroidX.Compose.Activity.plugin)
    implementation(platform (AndroidDependencies.AndroidX.Compose.Bom.plugin))
    implementation(AndroidDependencies.AndroidX.Compose.ui)
    implementation(AndroidDependencies.AndroidX.Compose.graphics)
    implementation(AndroidDependencies.AndroidX.Compose.toolingPreview)
    implementation(AndroidDependencies.AndroidX.Compose.Material.plugin)

    // DI
    implementation(AndroidDependencies.Di.Hilt.plugin)
    kapt(AndroidDependencies.Di.Hilt.compiler)

    implementation(AndroidDependencies.Logger.Timber.plugin)

    testImplementation(AndroidDependencies.Test.JUnit.plugin)
    androidTestImplementation(AndroidDependencies.AndroidX.Test.JUnit.plugin)
    androidTestImplementation(AndroidDependencies.AndroidX.Test.Espresso.plugin)
    androidTestImplementation(AndroidDependencies.AndroidX.Compose.Test.junit4)
    androidTestImplementation(platform (AndroidDependencies.AndroidX.Compose.Bom.plugin))
    debugImplementation(AndroidDependencies.AndroidX.Compose.Test.tooling)
    debugImplementation(AndroidDependencies.AndroidX.Compose.Test.manifest)
}