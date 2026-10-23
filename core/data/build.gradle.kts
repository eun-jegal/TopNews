import java.util.Properties

plugins {
    alias(libs.plugins.topnews.android.library)
    alias(libs.plugins.topnews.hilt)
    alias(libs.plugins.topnews.retrofit)
}

// NEWS_API_KEY lives in local.properties (gitignored) and is exposed only through BuildConfig.
val localProperties = Properties().apply {
    providers.fileContents(rootProject.layout.projectDirectory.file("local.properties"))
        .asText.orNull?.let { load(it.reader()) }
}
val newsApiKey: String = localProperties.getProperty("NEWS_API_KEY").orEmpty()
if (newsApiKey.isBlank()) {
    logger.warn("NEWS_API_KEY is missing from local.properties — network calls will fail.")
}

android {
    buildFeatures.buildConfig = true
    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://newsapi.org/v2/\"")
        buildConfigField("String", "NEWS_API_KEY", "\"$newsApiKey\"")
    }
}

dependencies {
    api(projects.core.domain)

    testImplementation(testFixtures(projects.core.domain))
}
