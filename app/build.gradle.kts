plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "FPT.PRO1122.Nhom3.DuAn1"
    compileSdk = 34

    defaultConfig {
        applicationId = "FPT.PRO1122.Nhom3.DuAn1"
        minSdk = 26
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
    // thu vien load anh
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    implementation(libs.material.v130alpha03)
    implementation(libs.firebase.database.v2004)
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

    implementation(libs.play.services.auth)
    implementation(libs.firebase.auth)
    implementation(libs.google.services)
    implementation(libs.google.firebase.auth)
    implementation(libs.com.google.firebase.firebase.auth)

    // Facebook
    implementation(platform(libs.firebase.bom))
    implementation(libs.android.facebook.android.sdk)
    implementation(libs.facebook.login)
}