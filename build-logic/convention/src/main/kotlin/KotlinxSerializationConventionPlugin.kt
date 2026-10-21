import com.ejcode.topnews.convention.libs
import com.ejcode.topnews.convention.library
import com.ejcode.topnews.convention.pluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinxSerializationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.pluginId("kotlin-serialization"))

        dependencies {
            add("implementation", libs.library("kotlinx-serialization-json"))
        }
    }
}
