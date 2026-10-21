import com.ejcode.topnews.convention.libs
import com.ejcode.topnews.convention.library
import com.ejcode.topnews.convention.pluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/** Applied to `:feature:<name>:presentation` modules. */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.pluginId("topnews-android-library"))
        pluginManager.apply(libs.pluginId("topnews-compose"))
        pluginManager.apply(libs.pluginId("topnews-hilt"))
        pluginManager.apply(libs.pluginId("topnews-kotlinx-serialization"))

        dependencies {
            add("implementation", project(":core:domain"))
            add("implementation", project(":core:presentation"))
            add("implementation", project(":core:design-system"))

            add("implementation", libs.library("androidx-lifecycle-runtime-compose"))
            add("implementation", libs.library("androidx-lifecycle-viewmodel-compose"))
            add("implementation", libs.library("androidx-navigation-compose"))
            add("implementation", libs.library("androidx-hilt-navigation-compose"))
        }
    }
}
