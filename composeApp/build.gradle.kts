import com.android.build.api.dsl.ApplicationExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlinSerialization)
}

// --- Supabase credentials (generated, never committed) ---------------------
// Resolved at configuration time from local.properties, then Gradle properties,
// then env vars. Blank values are valid: the app stays fully offline and the
// sign-in screen reports that sync is not configured (see OfflineAuthRepository).
val localSecrets: Map<String, String> = run {
    val text = providers.fileContents(
        rootProject.layout.projectDirectory.file("local.properties"),
    ).asText.orNull
    if (text.isNullOrBlank()) emptyMap()
    else Properties().apply { load(text.reader()) }
        .entries.associate { (k, v) -> k.toString() to v.toString() }
}

fun secretOrEmpty(key: String): String =
    localSecrets[key]
        ?: providers.gradleProperty(key).orNull
        ?: providers.environmentVariable(key).orNull
        ?: ""

val supabaseUrl: String = secretOrEmpty("SUPABASE_URL")
val supabaseAnonKey: String = secretOrEmpty("SUPABASE_ANON_KEY")
val generatedSupabaseConfigDir: Provider<Directory> =
    layout.buildDirectory.dir("generated/supabaseConfig/kotlin")

val generateSupabaseConfig = tasks.register("generateSupabaseConfig") {
    val outDir = generatedSupabaseConfigDir
    val url = supabaseUrl
    val key = supabaseAnonKey
    inputs.property("url", url)
    inputs.property("key", key)
    outputs.dir(outDir)
    doLast {
        val pkgDir = outDir.get().asFile.resolve("com/shah/cashwise/core/config")
        pkgDir.mkdirs()
        pkgDir.resolve("SupabaseConfig.kt").writeText(
            """
            |package com.shah.cashwise.core.config
            |
            |/**
            | * Supabase project credentials, generated at build time from
            | * local.properties / Gradle properties / env vars. Do NOT edit by hand.
            | * Blank values mean sync is not configured and the app stays offline-only.
            | */
            |internal object SupabaseConfig {
            |    const val URL: String = "$url"
            |    const val ANON_KEY: String = "$key"
            |}
            |
            """.trimMargin(),
        )
    }
}

// Generate the config before any Kotlin compilation across all targets.
tasks.matching { it.name.startsWith("compile") }.configureEach {
    dependsOn(generateSupabaseConfig)
}

kotlin {
    jvmToolchain(11)
    androidTarget()
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.sqldelight.android.driver)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.android)
            implementation(libs.ktor.client.android)
        }
        commonMain {
            kotlin.srcDir(generatedSupabaseConfigDir)
            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(libs.compose.ui)
                implementation(libs.compose.components.resources)
                implementation(libs.compose.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(libs.sqldelight.runtime)
                implementation(libs.androidx.datastore.preferences.core)
                implementation(libs.kotlinx.coroutines.core)
                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.logging)
                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor3)
                implementation(libs.qrose)
                implementation(project.dependencies.platform(libs.supabase.bom))
                implementation(libs.supabase.auth)
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
            implementation(libs.ktor.client.darwin)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.sqldelight.sqlite.driver)
            implementation(libs.ktor.client.cio)
        }
    }
}

extensions.configure<ApplicationExtension> {
    namespace = "com.shah.cashwise"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.shah.cashwise"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.shah.cashwise.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.shah.cashwise"
            packageVersion = "1.0.0"

            macOS { iconFile.set(project.file("icons/AppIcon.icns")) }
            windows { iconFile.set(project.file("icons/app-icon.ico")) }
            linux { iconFile.set(project.file("icons/icon-1024.png")) }
        }
    }
}

sqldelight {
    databases {
        create("CashWiseDatabase") {
            packageName.set("com.shah.cashwise.db")
        }
    }
}
