import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp").version("1.7.21-1.0.8")
    id("com.google.protobuf").version("0.8.17")
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

apply(from = "../buildscripts/jacoco.gradle")
apply(from = "../buildscripts/coveralls.gradle")

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.adammcneilly.toa"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "com.adammcneilly.toa.HiltTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isTestCoverageEnabled = true
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"

        freeCompilerArgs += listOf(
                "-Xopt-in=kotlin.time.ExperimentalTime",
                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
        )
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0-alpha02"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests.all {
            kover {
                isEnabled = true
//                excludes = listOf(
//                        "dagger.hilt.internal.aggregatedroot.codegen.*",
//                        "hilt_aggregated_deps.*",
//                        "com.adammcneilly.toa.core.di.*",
//                        "com.adammcneilly.toa.core.ui.theme.*",
//                        ".*ComposableSingletons.*",
//                        ".*Hilt.*",
//                        ".*BuildConfig.*",
//                        ".*_Factory.*",
//                )
            }
        }
    }

    sourceSets {
        getByName("test") {
            java.srcDir(project(":task-api-test").file("src/commonMain/kotlin"))
        }
    }

    applicationVariants.forEach { variant ->
        kotlin.sourceSets {
            getByName(variant.name) {
                kotlin.srcDir("build/generated/ksp/${variant.name}/kotlin")
            }
        }
    }
}

dependencies {

    lintChecks(project(":lint-checks"))
    implementation(project(":core-models"))
    implementation(project(":core-data"))
    implementation(project(":task-api"))
    implementation(libs.androidx.ktx.core)
    implementation("androidx.appcompat:appcompat:$rootProject.ext.versions.appCompat")
    implementation("com.google.android.material:material:$rootProject.ext.versions.material")
    implementation("androidx.compose.ui:ui:1.4.0-alpha04")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0-alpha04")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$rootProject.ext.versions.lifecycle")
    implementation("androidx.activity:activity-compose:$rootProject.ext.versions.activityCompose")
    implementation("com.google.dagger:hilt-android:$rootProject.ext.hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$rootProject.ext.versions.hiltNavigationCompose")
    implementation(libs.bundles.accompanist)
    // For the known issue: https://developer.android.com/jetpack/androidx/releases/compose-material3#1.1.0-alpha04
    implementation("androidx.compose.foundation:foundation:1.4.0-alpha03")
    implementation("androidx.compose.material3:material3:1.1.0-alpha04")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.0-alpha04")
    implementation("io.github.raamcosta.compose-destinations:animations-core:$rootProject.ext.versions.composeDestinations")
    implementation("androidx.navigation:navigation-compose:$rootProject.ext.versions.composeNavigation")
    implementation("com.google.accompanist:accompanist-navigation-animation:$rootProject.ext.versions.accompanist")
    implementation("androidx.room:room-runtime:$rootProject.ext.versions.room")
    implementation("androidx.room:room-ktx:$rootProject.ext.versions.room")
    implementation("androidx.window:window:$rootProject.ext.versions.windowManager")
    implementation("androidx.datastore:datastore:$rootProject.ext.versions.dataStore")
    implementation( "com.google.protobuf:protobuf-javalite:3.18.0")
    ksp("androidx.room:room-compiler:$rootProject.ext.versions.room")
    ksp("io.github.raamcosta.compose-destinations:ksp:$rootProject.ext.versions.composeDestinations")
    kapt("com.google.dagger:hilt-compiler:$rootProject.ext.hiltVersion")
    testImplementation("junit:junit:$rootProject.ext.versions.junit")
    testImplementation("io.mockk:mockk:$rootProject.ext.versions.mockk")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$rootProject.ext.versions.coroutinesTest")
    testImplementation("com.google.truth:truth:$rootProject.ext.versions.truth")
    testImplementation("app.cash.turbine:turbine:$rootProject.ext.versions.turbine")
    testImplementation(project(":task-api-test"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.0-alpha04")
    androidTestImplementation("androidx.test.ext:junit:$rootProject.ext.versions.androidxTestJunit")
    androidTestImplementation("androidx.test.espresso:espresso-core:$rootProject.ext.versions.espresso")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.0-alpha04")
    androidTestImplementation("com.google.truth:truth:$rootProject.ext.versions.truth")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$rootProject.ext.hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$rootProject.ext.hiltVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.0-alpha04")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.0-alpha04")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.12"
    }

    // Generates the java Protobuf-lite code for the Protobufs in this project. See
    // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
    // for more information.
    generateProtoTasks {
        ofSourceSet("main").forEach { task ->
            task.builtins {
                getByName("java") {
                    option("lite")
                }
            }
        }
    }
}
