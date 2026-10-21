import com.ejcode.topnews.convention.configureKotlinJvm
import com.ejcode.topnews.convention.configureUnitTests
import com.ejcode.topnews.convention.libs
import com.ejcode.topnews.convention.library
import com.ejcode.topnews.convention.pluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/** Pure Kotlin (JVM) module — no Android dependencies. */
class DomainModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.pluginId("kotlin-jvm"))
        pluginManager.apply(libs.pluginId("android-lint"))

        configureKotlinJvm()
        configureUnitTests()

        dependencies {
            // api: domain contracts expose Flow, so consumers need coroutines on their classpath
            add("api", libs.library("kotlinx-coroutines-core"))
            if (path != ":core:domain") {
                add("api", project(":core:domain"))
            }
        }
    }
}
