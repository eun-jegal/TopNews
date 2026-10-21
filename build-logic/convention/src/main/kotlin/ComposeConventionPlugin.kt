import com.android.build.api.dsl.CommonExtension
import com.ejcode.topnews.convention.bundle
import com.ejcode.topnews.convention.libs
import com.ejcode.topnews.convention.library
import com.ejcode.topnews.convention.pluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/** Apply after an Android application/library plugin. */
class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.pluginId("kotlin-compose"))

        extensions.getByType<CommonExtension>().buildFeatures.compose = true

        dependencies {
            val bom = platform(libs.library("androidx-compose-bom"))
            add("implementation", bom)
            add("implementation", libs.bundle("compose"))
            add("debugImplementation", libs.bundle("compose-debug"))

            add("androidTestImplementation", bom)
            add("androidTestImplementation", libs.library("androidx-compose-ui-test-junit4"))
            add("androidTestImplementation", libs.library("androidx-test-ext-junit"))
        }
    }
}
