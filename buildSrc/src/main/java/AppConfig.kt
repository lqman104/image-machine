import org.gradle.api.JavaVersion

/*
 * Initiated by Rahmad Hidayat on 7/25/22, 11:12 PM
 * Copyright (c) Qasir 2022 All rights reserved.
 */

object AppConfig {
    const val version = "1.0.0"
    const val versionCode = 1
    const val jvmTarget = "17"

    object Java {
        val version = JavaVersion.VERSION_17
    }

    object KotlinCompiler {
        val version = "1.4.7"
    }

    object Android {
        const val minSdk = 24
        const val compileSdk = 34
        const val targetSdk = 34
    }
}