plugins {
    alias(libs.plugins.android.library)
}

android {
    compileSdk = libs.versions.compileVersion.get().toInt()
    namespace = "com.script"
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
    defaultConfig {
        minSdk = libs.versions.minVersion.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    lint {
        checkDependencies = true
        targetSdk = libs.versions.targetVersion.get().toInt()
    }
    testOptions {
        targetSdk = libs.versions.targetVersion.get().toInt()
    }
    tasks.withType<JavaCompile>().configureEach {
        options.compilerArgs.add("-Xlint:deprecation")
    }
}

dependencies {
    api(libs.mozilla.rhino)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.okhttp)
    implementation(libs.androidx.collection)
}
