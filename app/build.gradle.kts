plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
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
        implementation("com.google.firebase:firebase-bom:32.7.2")
        implementation("com.google.firebase:firebase-analytics")
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
        implementation("com.google.android.gms:play-services-location:21.1.0")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        // PERSONALI
        implementation("androidx.core:core-splashscreen:1.0.0")
        implementation("androidx.cardview:cardview:1.0.0")

        implementation ("com.jjoe64:graphview:4.2.2")


        /*implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
         */

        val retrofit_version = "2.9.0"
        implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
        implementation ("com.squareup.retrofit2:converter-gson:$retrofit_version")

        // Coroutines
        val coroutines_version = "1.4.2"
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

        // Lifecycle
        val lifecycle_version = "2.2.0"
        implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
        implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")


        //implementation ("org.opencv:opencv-android:4.5.1")    // FAILED TO RESOLVE

        implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
        implementation("com.google.firebase:firebase-analytics")
        implementation("com.google.firebase:firebase-firestore")
        implementation("com.google.firebase:firebase-database")
        implementation ("com.google.firebase:firebase-firestore:24.10.0")
        implementation("com.google.firebase:firebase-auth")
        implementation("com.google.android.gms:play-services-auth:20.7.0")

            //VIDEOSORVEGLIANZA
            // Retrofit
            implementation ("com.squareup.retrofit2:retrofit:2.5.0")
            implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
            implementation ("com.squareup.retrofit2:converter-gson:2.2.0")
            implementation ("com.squareup.retrofit2:converter-scalars:2.5.0")
            implementation ("com.squareup.retrofit2:converter-simplexml:2.5.0")

            // OkHttp
            implementation ("com.squareup.okhttp3:okhttp:3.4.1")
            implementation ("com.squareup.okhttp3:logging-interceptor:3.4.1")

            // Rx
           // implementation ("io.reactivex.rxjava2:rxandroid:2.2.0")
            //implementation ("io.reactivex.rxjava2:rxjava:2.1.0")

            // Gson
            implementation ("com.google.code.gson:gson:2.8.2")

            // Jetty Server
            implementation ("org.eclipse.jetty:jetty-server:8.1.17.v20150415")
            implementation ("org.eclipse.jetty:jetty-servlet:8.1.17.v20150415")
            implementation ("org.eclipse.jetty:jetty-client:8.1.17.v20150415")



        }





