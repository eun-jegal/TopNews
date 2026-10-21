import androidx.room.gradle.RoomExtension
import com.ejcode.topnews.convention.libs
import com.ejcode.topnews.convention.library
import com.ejcode.topnews.convention.pluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.pluginId("ksp"))
        pluginManager.apply(libs.pluginId("room"))

        extensions.configure<RoomExtension> {
            schemaDirectory("$projectDir/schemas")
        }

        dependencies {
            add("implementation", libs.library("androidx-room-runtime"))
            add("implementation", libs.library("androidx-room-ktx"))
            add("ksp", libs.library("androidx-room-compiler"))
        }
    }
}
