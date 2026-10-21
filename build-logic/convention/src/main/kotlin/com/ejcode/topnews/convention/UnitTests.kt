package com.ejcode.topnews.convention

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

/** Local unit tests run on JUnit 5 (Jupiter). Instrumented / Compose UI tests stay on JUnit 4. */
internal fun Project.configureUnitTests() {
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
    dependencies {
        add("testImplementation", libs.bundle("unit-test"))
        add("testRuntimeOnly", libs.library("junit-platform-launcher"))
    }
}
