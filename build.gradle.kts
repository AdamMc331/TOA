// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.21")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.19.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.29.0")
        classpath("org.jetbrains.kotlinx:kover:0.4.1")
        classpath("com.hiya:jacoco-android:0.2")
        classpath("gradle.plugin.org.kt3k.gradle.plugin:coveralls-gradle-plugin:2.12.0")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("org.jmailen.kotlinter").version("3.13.0").apply(false)
}

apply(from = "buildscripts/git-hooks.gradle")
apply(from = "buildscripts/versions.gradle")

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    }
}

subprojects {
    apply(from = "../buildscripts/ktlint.gradle")
    apply(from = "../buildscripts/detekt.gradle")
    apply(from = "../buildscripts/versionsplugin.gradle")
    apply(from = "../buildscripts/kover.gradle")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

afterEvaluate {
    // We install the hook at the first occasion
    tasks.named("clean") {
        dependsOn(":installGitHooks")
    }
}
