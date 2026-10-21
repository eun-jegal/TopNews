plugins {
    alias(libs.plugins.topnews.domain)
    // Shared fakes consumed via testImplementation(testFixtures(projects.core.domain))
    `java-test-fixtures`
}
