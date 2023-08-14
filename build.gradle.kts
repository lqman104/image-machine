buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(GradleProject.Android.application) version GradleProject.Android.version apply false
    id(GradleProject.Android.library) version GradleProject.Android.version apply false
    id(GradleProject.Kotlin.plugin) version GradleProject.Kotlin.version apply false
    id(GradleProject.Hilt.plugin) version GradleProject.Hilt.version apply false
}