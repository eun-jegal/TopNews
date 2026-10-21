package com.ejcode.topnews.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun VersionCatalog.version(alias: String): String = findVersion(alias).get().requiredVersion

fun VersionCatalog.library(alias: String) = findLibrary(alias).get()

fun VersionCatalog.bundle(alias: String) = findBundle(alias).get()

fun VersionCatalog.pluginId(alias: String): String = findPlugin(alias).get().get().pluginId

/** `:core:design-system` → `com.ejcode.topnews.core.designsystem` */
val Project.defaultNamespace: String
    get() = "com.ejcode.topnews." + path.removePrefix(":").replace(':', '.').replace("-", "")
