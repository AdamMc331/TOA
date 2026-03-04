// import com.google.protobuf.gradle.builtins
// import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.protobuf
// import com.google.protobuf.gradle.protoc

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.cash.paparazzi)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.protobuf)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.adammcneilly.toa"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableUnitTestCoverage = true
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets {
        getByName("test") {
            java.srcDir(project(":task-api-test").file("src/commonMain/kotlin"))
        }
    }

    applicationVariants.forEach { variant ->
        variant.sourceSets.forEach { sourceSet ->
            sourceSet.javaDirectories += files("build/generated/ksp/${variant.name}/kotlin")
        }
    }

    namespace = "com.adammcneilly.toa"
}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    debugImplementation(composeBom)
    androidTestImplementation(composeBom)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.google.truth)
    androidTestImplementation(libs.hilt.android.testing)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowsizeclass)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.ktx.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.window)
    implementation(libs.bundles.accompanist)
    implementation(libs.compose.destinations.animations.core)
    implementation(libs.google.protobuf.javalite)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(project(":core-data"))
    implementation(project(":core-models"))
    implementation(project(":task-api"))
    ksp(libs.hilt.compiler)
    // add("kspAndroidTest", libs.hilt.android.compiler)
    ksp(libs.androidx.room.compiler)
    ksp(libs.compose.destinations.ksp)
//    lintChecks(project(":lint-checks"))
    testImplementation(libs.cash.turbine)
    testImplementation(libs.google.truth)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(project(":task-api-test"))
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.get()}"
    }

    // Generates the java Protobuf-lite code for the Protobufs in this project. See
    // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
    // for more information.
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}
