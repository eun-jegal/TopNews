import com.android.build.api.dsl.ApplicationExtension
import com.ejcode.topnews.convention.configureKotlinAndroid
import com.ejcode.topnews.convention.configureUnitTests
import com.ejcode.topnews.convention.libs
import com.ejcode.topnews.convention.pluginId
import com.ejcode.topnews.convention.version
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.pluginId("android-application"))
        pluginManager.apply(libs.pluginId("topnews-compose"))
        pluginManager.apply(libs.pluginId("topnews-hilt"))

        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)
            defaultConfig {
                targetSdk = libs.version("targetSdk").toInt()
                versionCode = 1
                versionName = "1.0"
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            buildFeatures.buildConfig = true
        }
        configureUnitTests()
    }
}
