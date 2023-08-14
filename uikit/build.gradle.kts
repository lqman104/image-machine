plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.luqman.imagemachine.uikit"
    compileSdk = AppConfig.Android.compileSdk

    defaultConfig {
        minSdk = AppConfig.Android.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.KotlinCompiler.version
    }
}

dependencies {
    implementation(AndroidDependencies.AndroidX.CoreKtx.plugin)
    implementation(AndroidDependencies.Logger.Timber.plugin)
    implementation(platform (AndroidDependencies.AndroidX.Compose.Bom.plugin))
    implementation(AndroidDependencies.AndroidX.Compose.ui)
    implementation(AndroidDependencies.AndroidX.Compose.graphics)
    implementation(AndroidDependencies.AndroidX.Compose.toolingPreview)
    debugImplementation(AndroidDependencies.AndroidX.Compose.Test.tooling)
    implementation(AndroidDependencies.AndroidX.Compose.Material.plugin)
    // IMAGE LOADER
    implementation(AndroidDependencies.Util.ImageLoader.plugin)

}