plugins {
    alias(libs.plugins.android.library)
}

android {
    compileSdk = libs.versions.compileVersion.get().toInt()
    namespace = "me.ag2s"
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
    defaultConfig {
        minSdk = libs.versions.minVersion.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(libs.androidx.annotation)
}
