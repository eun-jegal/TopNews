import com.android.build.api.dsl.LibraryExtension
import com.ejcode.topnews.convention.configureKotlinAndroid
import com.ejcode.topnews.convention.configureUnitTests
import com.ejcode.topnews.convention.defaultNamespace
import com.ejcode.topnews.convention.libs
import com.ejcode.topnews.convention.pluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.pluginId("android-library"))

        extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)
            namespace = defaultNamespace
            defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        configureUnitTests()
    }
}
