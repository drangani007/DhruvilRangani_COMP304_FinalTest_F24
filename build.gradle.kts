// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

// Define kotlin_version
val kotlin_version by extra("1.9.20")

// Optional: Ensure all subprojects use the same Kotlin version
subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            languageVersion = "1.9"
        }
    }
}