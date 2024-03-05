import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.vcroitor.codemagic_test"
    compileSdk = 34

    val keysDevProperties = Properties()
    keysDevProperties.load(file("../signing/development/carl-development-keystore.properties").inputStream())

    val keysQaProperties = Properties()
    keysQaProperties.load(file("../signing/qa/carl-qa-keystore.properties").inputStream())

    val keysProdProperties = Properties()
    keysProdProperties.load(file("../signing/production/carl-production-keystore.properties").inputStream())

    signingConfigs {
        create("development") {
            storeFile = file("../signing/development/${keysDevProperties.getProperty("dev.storeFileName")}")
            storePassword = keysDevProperties.getProperty("dev.storePassword")
            keyAlias = keysDevProperties.getProperty("dev.keyAlias")
            keyPassword = keysDevProperties.getProperty("dev.keyPassword")
        }
        create("production") {
            storeFile = file("../signing/production/${keysProdProperties.getProperty("prod.storeFileName")}")
            storePassword = keysProdProperties.getProperty("prod.storePassword")
            keyAlias = keysProdProperties.getProperty("prod.keyAlias")
            keyPassword = keysProdProperties.getProperty("prod.keyPassword")
        }
        create("qa") {
            storeFile = file("../signing/qa/${keysQaProperties.getProperty("qa.storeFileName")}")
            storePassword = keysQaProperties.getProperty("qa.storePassword")
            keyAlias = keysQaProperties.getProperty("qa.keyAlias")
            keyPassword = keysQaProperties.getProperty("qa.keyPassword")
        }
    }

    defaultConfig {
        applicationId = "com.vcroitor.codemagic_test"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        signingConfig = signingConfigs.getByName("development")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
        }
        debug {
            signingConfig = null
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    flavorDimensions.add("env")
    productFlavors {
        create("development") {
            dimension = "env"
            signingConfig = signingConfigs.getByName("development")
        }
        create("production") {
            dimension = "env"
            signingConfig = signingConfigs.getByName("production")
        }
        create("qa") {
            dimension = "env"
            signingConfig = signingConfigs.getByName("qa")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}