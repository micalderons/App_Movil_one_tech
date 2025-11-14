plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.example.one_teach"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.one_teach"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        // El bloque 'release' estaba duplicado, he fusionado el contenido.
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Ajusta esto para tu entorno de producción (HTTPS recomendado)
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8080/\"")
        }
        debug {
            // Base URL para entorno de desarrollo (Emulador Android -> host máquina)
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8080/\"")
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
        buildConfig = true
    }
}

dependencies {

    // --- Dependencias Principales de AndroidX ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose) // Usando la versión de libs o la más reciente
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2") // Unificado

    // --- Compose Bill of Materials (BOM) ---
    // El BOM gestiona las versiones de las librerías de Compose para que sean compatibles entre sí.
    implementation(platform(libs.androidx.compose.bom))

    // --- Dependencias de Jetpack Compose (SIN especificar versión, el BOM lo hace por ti) ---
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.lifecycle:lifecycle-runtime-compose") // Versión gestionada por BOM o compatible
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2") // Especificar versión si no está en BOM

    // --- Navegación en Compose ---
    // Usamos la versión más reciente que tenías.
    implementation("androidx.navigation:navigation-compose:2.8.2")

    // --- Networking (Retrofit y OkHttp) ---
    // Estas ya estaban correctas.
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // --- Otras Dependencias (sin duplicados) ---
    implementation("io.coil-kt:coil-compose:2.7.0") // Para cargar imágenes
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("com.google.android.material:material:1.12.0") // Para componentes de Material Design (no-Compose)
    implementation("androidx.window:window:1.3.0")

    // --- Dependencias de Test ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // --- Dependencias de Debug ---
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
