package app.robot.sindarin_dictionary_en

object Libraries {
    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
        const val coroutinesGms = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.kotlinCoroutines}"
        const val reflection = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        const val lifecycle = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
    }

    object Koin {
        const val koin = "io.insert-koin:koin-android:${Versions.koin}"
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
        /*const val scope = "io.insert-koin:koin-androidx-scope:${Versions.koin}"
        const val viewModel = "io.insert-koin:koin-androidx-viewmodel:${Versions.koin}"
        const val ext = "io.insert-koin:koin-androidx-ext:${Versions.koin}"
        const val test = "io.insert-koin:koin-test:${Versions.koin}"*/
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
        const val lazyColumnScrollbar = "com.github.nanihadesuka:LazyColumnScrollbar:${Versions.lazyColumnScrollbar}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraintLayout}"
    }

    object NavigationCompose {
        const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
        const val navigationRuntime = "androidx.navigation:navigation-runtime-ktx:${Versions.navigationCompose}"
    }

    object ComposeDestinations {
        const val destinations = "io.github.raamcosta.compose-destinations:core:${Versions.composeDestinations}"
        const val ksp = "io.github.raamcosta.compose-destinations:ksp:${Versions.composeDestinations}"
    }

    object Firebase {
        const val firestore = "com.google.firebase:firebase-firestore:${Versions.firebaseFirestore}"
    }

    object Support {
        const val core = "androidx.core:core-ktx:${Versions.androidxCore}"
        const val lifeCycle = "androidx.core:core-ktx:${Versions.androidxCore}"
        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object Json {
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
    }

    object Database {
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val ktx = "androidx.room:room-ktx:${Versions.room}"
        const val compile = "androidx.room:room-compiler:${Versions.room}"
        const val paging = "androidx.room:room-paging:${Versions.room}"
    }

    object Paging {
        const val runtime = "androidx.paging:paging-runtime:${Versions.paging}"
        const val compose = "androidx.paging:paging-compose:${Versions.pagingCompose}"
    }

    object Utils {
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    }

    object Ads {
        const val admob = "com.google.android.gms:play-services-ads:${Versions.admob}"
    }
}