buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
    repositories{
        google()
        maven{
            url = uri("https://maven.pkg.jetbrains.space/data2viz/p/maven/dev")
        }
        maven{
            url = uri ("https://maven.pkg.jetbrains.space/data2viz/p/maven/public")
        }

    }
}


// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}

