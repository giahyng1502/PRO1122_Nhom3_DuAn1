plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "FPT.PRO1122.Nhom3.DuAn1"
    compileSdk = 34

    defaultConfig {
        applicationId = "FPT.PRO1122.Nhom3.DuAn1"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Glide
    implementation (libs.glide.v4120)
    annotationProcessor (libs.compiler)

    // Google Material
    implementation (libs.material.v190)

    // Firebase
    implementation (libs.google.firebase.database.v2004)
    implementation (libs.com.google.firebase.firebase.auth2)
    implementation (libs.gson)

    // AndroidX Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.car.ui.lib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Play Services
    implementation(libs.play.services.maps)
    implementation(libs.play.services.auth)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)

    // Facebook
    implementation(platform(libs.firebase.bom))
    implementation(libs.android.facebook.android.sdk)
    implementation(libs.facebook.login)

    // Google Maps and Places
    implementation(libs.android.maps.utils)
    implementation(libs.places)

    // Image gif
    implementation (libs.android.gif.drawable)
}
