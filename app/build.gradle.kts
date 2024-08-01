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
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation(libs.legacy.support.v4)
    implementation(libs.recyclerview)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    // Google Material
    implementation ("com.google.android.material:material:1.9.0")

    // Firebase
    implementation ("com.google.firebase:firebase-database:20.0.4")
    implementation ("com.google.firebase:firebase-auth:22.1.1")
    implementation ("com.google.code.gson:gson:2.10.1")

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
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Facebook
    implementation(platform(libs.firebase.bom))
    implementation(libs.android.facebook.android.sdk)
    implementation(libs.facebook.login)

    // Google Maps and Places
    implementation("com.google.maps.android:android-maps-utils:2.3.0")
    implementation("com.google.android.libraries.places:places:3.5.0")
}
