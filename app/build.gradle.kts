plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.kamalnayan.knstarwars"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kamalnayan.knstarwars"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "BASE_URL", "\"https://swapi.dev/api/\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(Module.commons))
    implementation(project(Module.data))
    implementation(project(Module.domain))
    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.navigationUi)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.junitExt)
    androidTestImplementation(Dependencies.espresso)

    implementation(Dependencies.lifecycleKtx)
    implementation(Dependencies.lifecycleViewModelKtx)
    implementation(Dependencies.fragmentKtx)

    implementation(Dependencies.livedataKtx)


    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)


    // epoxy
    implementation(Dependencies.epoxy)
    implementation(Dependencies.epoxyPaging)
    implementation(Dependencies.epoxyDataBinding)
    kapt(Dependencies.epoxyProcessor)

    implementation(Dependencies.coroutineAndroid)
    implementation(Dependencies.kotlinStdLib)

    implementation(Dependencies.shimmer)

    api(Dependencies.room)
    api(Dependencies.roomKtx)
    api(Dependencies.roomPaging)
    api(Dependencies.paging)
    kapt(Dependencies.roomCompiler)

}

kapt {
    correctErrorTypes = true
}