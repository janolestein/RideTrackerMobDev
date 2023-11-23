plugins {
    id("com.android.application")
    id ("com.google.dagger.hilt.android")

}

android {
    namespace = "com.jole.ridetrackermobdev"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jole.ridetrackermobdev"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.jole.ridetrackermobdev.hilt.HiltCustomTestRunner"
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
}

dependencies {

    implementation ("org.osmdroid:osmdroid-android:6.1.17")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.fragment:fragment:1.6.2")
    implementation("androidx.preference:preference:1.2.1")
    implementation("androidx.test:rules:1.5.0")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.dagger:hilt-android:2.44")
    annotationProcessor ("com.google.dagger:hilt-compiler:2.44")
    implementation ("androidx.room:room-runtime:2.6.0")
    annotationProcessor ("androidx.room:room-compiler:2.6.0")
    testImplementation ("androidx.room:room-testing:2.6.0")
    implementation ("androidx.room:room-paging:2.6.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    testAnnotationProcessor ("com.google.dagger:hilt-android-compiler:2.44")
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.44")
    androidTestAnnotationProcessor ("com.google.dagger:hilt-android-compiler:2.44")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("org.mockito:mockito-core:3.10.0")
    androidTestImplementation ("org.mockito:mockito-android:3.10.0")


}

