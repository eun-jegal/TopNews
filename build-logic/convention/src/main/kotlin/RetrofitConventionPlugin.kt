import com.ejcode.topnews.convention.libs
import com.ejcode.topnews.convention.library
import com.ejcode.topnews.convention.pluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.pluginId("topnews-kotlinx-serialization"))

        dependencies {
            add("implementation", libs.library("retrofit"))
            add("implementation", libs.library("retrofit-converter-kotlinx-serialization"))
            add("implementation", libs.library("okhttp"))
            add("implementation", libs.library("okhttp-logging-interceptor"))
            add("testImplementation", libs.library("okhttp-mockwebserver"))
        }
    }
}
