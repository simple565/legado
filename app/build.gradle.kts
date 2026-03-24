import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.services)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.org.jetbrains.kotlin.compose)
}

apply(from = "download.gradle")

android {
    namespace = "io.legado.app"
    compileSdk = libs.versions.compileVersion.get().toInt()

    defaultConfig {
        applicationId = "io.legado.app"
        minSdk = libs.versions.minVersion.get().toInt()
        targetSdk = libs.versions.targetVersion.get().toInt()
        versionCode = providers.exec {
            commandLine(
                "git",
                "rev-list",
                "--count",
                "HEAD"
            )
        }.standardOutput.asText.get().trim().toInt()
        versionName = "3.${SimpleDateFormat("yy.MMddHH").format(Date())}"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "Cronet_Version", "\"${project.findProperty("CronetVersion")}\"")
        buildConfigField(
            "String",
            "Cronet_Main_Version",
            "\"${project.findProperty("CronetMainVersion")}\""
        )
    }
    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
    }
    signingConfigs {
        if (project.hasProperty("RELEASE_STORE_FILE")) {
            create("myConfig") {
                storeFile = file(project.property("RELEASE_STORE_FILE") as String)
                storePassword = project.property("RELEASE_STORE_PASSWORD") as String
                keyAlias = project.property("RELEASE_KEY_ALIAS") as String
                keyPassword = project.property("RELEASE_KEY_PASSWORD") as String
                enableV1Signing = true
                enableV2Signing = true
                enableV3Signing = true
                enableV4Signing = true
            }
        }
    }
    buildTypes {
        getByName("release") {
            applicationIdSuffix = ".release"
            if (project.hasProperty("RELEASE_STORE_FILE")) {
                signingConfig = signingConfigs.getByName("myConfig")
            }
            manifestPlaceholders["app_name"] = "@string/app_name"
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "cronet-proguard-rules.pro"
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            if (project.hasProperty("RELEASE_STORE_FILE")) {
                signingConfig = signingConfigs.getByName("myConfig")
            }
            manifestPlaceholders["app_name"] = "@string/app_name"
            versionNameSuffix = "_debug"
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "cronet-proguard-rules.pro"
            )
        }
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "arm64-v8a")
            isUniversalApk = true
        }
    }

    flavorDimensions += "mode"
    productFlavors {
        create("app") {
            dimension = "mode"
            manifestPlaceholders["APP_CHANNEL_VALUE"] = "app"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }

    sourceSets {
        getByName("androidTest").assets.srcDirs("$projectDir/schemas")
    }

    lint {
        checkDependencies = true
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.compose)

    debugImplementation(libs.androidx.compose.ui.tooling)


    coreLibraryDesugaring(libs.desugar)
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidTest)


    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.coroutines)


    // androidX
    implementation(libs.core.ktx)
    implementation(libs.appcompat.appcompat)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.preference.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.webkit)
    implementation(libs.androidx.documentfile)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    androidTestImplementation(libs.androidx.room.testing)

    // google
    implementation(libs.material)
    implementation(libs.flexbox)
    implementation(libs.gson)

    // lifecycle
    implementation(libs.lifecycle.common.java8)
    implementation(libs.lifecycle.service)

    implementation(libs.renderscript.intrinsics.replacement.toolkit)
    // media
    implementation(libs.media.media)
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.datasource.okhttp)
    // videoPlayer
    implementation(libs.gsyVideoPlayer.java)
    implementation(libs.gsyVideoPlayer.exo2)
    // 弹幕
    implementation(libs.danmakuFlameMaster)
    implementation(libs.lyricViewx)


    // Splitties
    implementation(libs.splitties.appctx)
    implementation(libs.splitties.systemservices)
    implementation(libs.splitties.views)



    // liveEventBus
    implementation(libs.liveeventbus)

    // 规则相关
    implementation(libs.jsoup)
    implementation(libs.json.path)
    implementation(libs.jsoupxpath)
    implementation(project(":modules:book"))
    implementation(project(":modules:rhino"))


    // 网络
    implementation(fileTree(mapOf("dir" to "cronetlib", "include" to listOf("*.jar", "*.aar"))))
    implementation(libs.okhttp)
    implementation(libs.protobuf.javalite)

    // Glide
    implementation(libs.glide.glide)
    implementation(libs.glide.okhttp)
    ksp(libs.glide.ksp)

    // Svg
    implementation(libs.androidsvg)
    // Glide svg plugin
    implementation(libs.glide.svg)

    // webServer
    implementation(libs.nanohttpd.nanohttpd)
    implementation(libs.nanohttpd.websocket)

    // 二维码
    implementation(libs.zxing.lite)

    // 颜色选择
    implementation(libs.colorpicker)

    // 压缩解压
    implementation(libs.libarchive)

    // apache
    implementation(libs.commons.text)

    // markdown
    implementation(libs.markwon.core)
    implementation(libs.markwon.image.glide)
    implementation(libs.markwon.ext.tables)
    implementation(libs.markwon.html)

    implementation(libs.quick.chinese.transfer.core)

    implementation(libs.hutool.crypto)

    // firebase, 崩溃统计和性能统计
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perf)

    implementation(libs.glide.recyclerview)

    // LeakCanary, 内存泄露检测
    // debugImplementation('com.squareup.leakcanary:leakcanary-android:2.7')

    // sora-editor代码编辑器,更丰富的编辑功能
    implementation(platform(libs.soraEditor.bom))
    implementation(libs.soraEditor.core)
    implementation(libs.soraEditor.language.textmate)
}