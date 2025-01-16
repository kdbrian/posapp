import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

val emulatorLocalHost: String =
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
    localProperties.getProperty("emulatorLocalhost").removeSurrounding("\"")
}else if (System.getenv("ngroemulatorLocalhostkHost") != null)
    System.getenv("emulatorLocalhost")
else
    "Set host url in env file"

val ngrokHost: String =
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
    localProperties.getProperty("ngrokHost").removeSurrounding("\"")
}else if (System.getenv("ngrokHost") != null)
    System.getenv("ngrokHost")
else
    "Set host url in env file"

val pcLocalHost: String =
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
    localProperties.getProperty("pcLocalhost").removeSurrounding("\"")
}else if (System.getenv("pcLocalhost") != null)
    System.getenv("pcLocalhost")
else
    "Set host url in env file"



plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    //graphql
    id("com.apollographql.apollo").version("4.1.0")
    kotlin("plugin.serialization") version "2.1.0"

    //secrets
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}

android {
    namespace = "io.kdbrian.minipos.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.kdbrian.minipos.android"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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


    //graphql
    implementation(libs.apollo.runtime)


    implementation(libs.kotlinx.serialization.json)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)

    implementation(libs.androidx.runtime.livedata)

    implementation(libs.timber)


    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //navigation
    implementation(libs.androidx.navigation.compose)

    //charts
    implementation(libs.compose.charts)


}


apollo {
    service("refreshPosSchema") {
        packageName.set("src.main.graphql")
        introspection {
            endpointUrl.set("${pcLocalHost}graphql")
            schemaFile.set(file("src/main/graphql/io/kdbrian/minipos/android/schema.graphqls"))
        }
    }
}