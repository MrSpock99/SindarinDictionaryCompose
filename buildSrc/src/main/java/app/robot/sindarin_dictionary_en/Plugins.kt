package app.robot.sindarin_dictionary_en

object Plugins {
    object ClassPaths {
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val composeCompiler = "org.jetbrains.kotlin:compose-compiler-gradle-plugin:${Versions.kotlin}"
        const val ksp = "com.google.devtools.ksp:symbol-processing-gradle-plugin:${Versions.ksp}"
        const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    }

    const val ksp = "com.google.devtools.ksp"
    const val kotlinKapt = "org.jetbrains.kotlin.kapt"
    const val kotlinAndroid = "kotlin-android"
    const val library = "com.android.library"
    const val application = "com.android.application"
    const val googleServices = "com.google.gms.google-services"
    const val composeCompiler = "org.jetbrains.kotlin.plugin.compose"
}
