import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.ejcode.topnews.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.topnews.android.application.get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.topnews.android.library.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = libs.plugins.topnews.android.feature.get().pluginId
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("domainModule") {
            id = libs.plugins.topnews.domain.get().pluginId
            implementationClass = "DomainModuleConventionPlugin"
        }
        register("compose") {
            id = libs.plugins.topnews.compose.get().pluginId
            implementationClass = "ComposeConventionPlugin"
        }
        register("hilt") {
            id = libs.plugins.topnews.hilt.get().pluginId
            implementationClass = "HiltConventionPlugin"
        }
        register("retrofit") {
            id = libs.plugins.topnews.retrofit.get().pluginId
            implementationClass = "RetrofitConventionPlugin"
        }
        register("room") {
            id = libs.plugins.topnews.room.get().pluginId
            implementationClass = "RoomConventionPlugin"
        }
        register("kotlinxSerialization") {
            id = libs.plugins.topnews.kotlinx.serialization.get().pluginId
            implementationClass = "KotlinxSerializationConventionPlugin"
        }
    }
}
