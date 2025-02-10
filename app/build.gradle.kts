plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.moneytransferapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.moneytransferapp"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3" // Compatible with Kotlin 1.8.10
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    implementation("androidx.activity:activity-compose:1.10.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-service:2.8.7")
    implementation("com.google.firebase:firebase-auth-ktx:23.1.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Compose Libraries (with Kotlin 1.8.10 compatible versions)
    implementation("androidx.compose.ui:ui:1.6.0") // Compatible with Kotlin 1.8.10
    implementation("androidx.compose.material:material:1.6.0") // Compatible with Kotlin 1.8.10
    implementation("androidx.navigation:navigation-compose:2.6.0") // Compatible with Kotlin 1.8.10
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.compose.runtime:runtime:1.6.0") // Compatible with Kotlin 1.8.10
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.0") // Compatible with Kotlin 1.8.10

    // Koin (compatible with Kotlin 1.8.10)
    implementation("io.insert-koin:koin-androidx-compose:3.1.6") // Compatible with Kotlin 1.8.10
    implementation("io.insert-koin:koin-android:3.1.6") // Compatible with Kotlin 1.8.10

    // Retrofit, OkHttp and Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Room Database (compatible with Kotlin 1.8.10)
    implementation("androidx.room:room-runtime:2.5.1") // Compatible with Kotlin 1.8.10
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    kapt("androidx.room:room-compiler:2.5.1") // Compatible with Kotlin 1.8.10
    implementation("androidx.room:room-ktx:2.5.1") // Compatible with Kotlin 1.8.10

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Navigation Compose (compatible with Kotlin 1.8.10)
    implementation("androidx.navigation:navigation-compose:2.6.0") // Compatible with Kotlin 1.8.10

    implementation(platform("com.google.firebase:firebase-bom:30.0.0")) // Compatible with Kotlin 1.8.10
    implementation("com.google.accompanist:accompanist-pager:0.24.0-alpha")
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-config-ktx")
    implementation("androidx.datastore:datastore-preferences:1.1.2")


}
