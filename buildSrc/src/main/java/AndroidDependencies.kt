object AndroidDependencies {
    object AndroidX {
        object Paging {
            private const val version = "3.2.0-rc01"
            val core = "androidx.paging:paging-runtime:$version"
            val compose = "androidx.paging:paging-compose:$version"
        }

        object Navigation {
            private const val version = "2.6.0"
            val plugin = "androidx.navigation:navigation-compose:$version"
        }

        object Compose {
            private const val version = "1.4.3"

            val ui = "androidx.compose.ui:ui:$version"
            val graphics = "androidx.compose.ui:ui-graphics:$version"
            val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"

            object ViewModel {
                private const val version = "2.6.1"
                val plugin = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
            }

            object Bom {
                private const val version = "2022.10.00"
                val plugin = "androidx.compose:compose-bom:$version"
            }

            object Activity {
                private const val version = "1.5.1"
                val plugin = "androidx.activity:activity-compose:$version"
            }

            object Material {
                private const val version = "1.1.0"
                val plugin = "androidx.compose.material3:material3:$version"
            }

            object Test {
                val junit4 = "androidx.compose.ui:ui-test-junit4:$version"
                val tooling = "androidx.compose.ui:ui-tooling:$version"
                val manifest = "androidx.compose.ui:ui-test-manifest:$version"
            }
        }

        object Room {
            private const val version = "2.5.2"

            val runtime = "androidx.room:room-runtime:$version"
            val ktx = "androidx.room:room-ktx:$version"
            val compiler = "androidx.room:room-compiler:$version"
            val testing = "androidx.room:room-testing:$version"
        }

        object CoreKtx {
            private const val version = "1.8.0"
            val plugin = "androidx.core:core-ktx:$version"

        }

        object Lifecycle {
            private const val version = "2.3.1"
            val plugin = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        }

        object Test {
            object JUnit {
                private const val version = "1.1.5"
                val plugin = "androidx.test.ext:junit:$version"
            }

            object Espresso {
                private const val version = "3.5.1"
                val plugin = "androidx.test.espresso:espresso-core:$version"
            }
        }
    }

    object Util {
        object ImageLoader {
            private const val version = "2.4.0"
            const val plugin = "io.coil-kt:coil-compose:$version"
        }
    }

    object Logger {
        object Timber {
            private const val version = "4.7.1"
            const val plugin = "com.jakewharton.timber:timber:$version"
        }
    }

    object Kotlin {
        object Coroutine {
            private const val version = "1.3.9"
            val plugin = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            val testPlugin = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object Di {
        object Hilt {
            private const val version = "2.44"
            private const val versionNavigation = "1.0.0"
            val plugin = "com.google.dagger:hilt-android:$version"
            val compiler = "com.google.dagger:hilt-compiler:$version"
            val navigation = "androidx.hilt:hilt-navigation-compose:$versionNavigation"
        }
    }

    object Test {
        object JUnit {
            private const val version = "4.13.2"
            val plugin = "junit:junit:$version"
        }

        object Mockito {
            private const val version = "5.4.0"
            val plugin = "org.mockito:mockito-core:$version"
        }
    }


}