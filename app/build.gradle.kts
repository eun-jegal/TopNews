plugins {
    alias(libs.plugins.topnews.android.application)
}

android {
    namespace = "com.ejcode.topnews"

    defaultConfig {
        applicationId = "com.ejcode.topnews"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.presentation)
    implementation(projects.core.designSystem)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    androidTestImplementation(libs.androidx.espresso.core)
}
