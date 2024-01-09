plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    kotlin("kapt")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(Module.commons))
    implementation(project(Module.data))
    implementation(project(Module.domain))

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
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
}

kapt {
    correctErrorTypes = true
}