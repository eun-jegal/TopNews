plugins {
    alias(libs.plugins.topnews.android.library)
    alias(libs.plugins.topnews.compose)
}

dependencies {
    api(projects.core.domain)
    implementation(libs.androidx.lifecycle.runtime.compose)
}
