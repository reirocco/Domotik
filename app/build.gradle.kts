plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    //id("kotlin-kapt")
    id ("kotlin-parcelize")

}

android {
    namespace = "com.example.domotik"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.domotik"
        minSdk = 24
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
            }


}

    dependencies {

        implementation("androidx.core:core-ktx:1.9.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.10.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
        implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
        implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
        implementation("androidx.camera:camera-view:1.3.0")
        //implementation("com.google.firebase:firebase-auth:23.0.0")
        implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
        implementation("androidx.databinding:databinding-runtime:8.2.0")
        implementation("com.google.firebase:firebase-database-ktx:20.3.0")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        // PERSONALI
        implementation("androidx.core:core-splashscreen:1.0.0")
        implementation("androidx.cardview:cardview:1.0.0")
        /*implementation("com.squareup.retrofit2:converter-gson")
    implementation("com.squareup.retrofit2:converter-jackson")
    implementation("com.squareup.retrofit2:converter-moshi")
    implementation("com.squareup.retrofit2:converter-protobuf")
    implementation("com.squareup.retrofit2:converter-wire")
    implementation("com.squareup.retrofit2:converter-simplexml")
    implementation("com.squareup.retrofit2:converter-jaxb")*/
        //implementation ("org.opencv:opencv-android:4.5.1") per video api
        implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
        implementation("com.google.firebase:firebase-analytics")
        implementation("com.google.firebase:firebase-firestore")
        implementation("com.google.firebase:firebase-database")
        implementation ("com.google.firebase:firebase-firestore:24.10.0")
        implementation("com.google.firebase:firebase-auth")
        implementation("com.google.android.gms:play-services-auth:20.7.0")

    }



