plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.example.randomuser"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.randomuser"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    val retrofitVersion = "2.9.0"
    val daggerVersion = "2.52"

    // Retrofit for api integration
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    // Retrofit with json converter
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    // Retrofit rxjava adapter
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")

    // RxJava for multithreading process
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.10")

    // dagger for dependency injection
    implementation("com.google.dagger:dagger:$daggerVersion")
    implementation("com.google.dagger:dagger-android-support:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    kapt("com.google.dagger:dagger-android-processor:$daggerVersion")

    // RecycleView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // backward compatibility
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Android lifecycle extensions
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Swipe Refresh Layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}