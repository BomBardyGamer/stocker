plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    val kotlinVersion = "1.6.10"
    implementation(kotlin("gradle-plugin", kotlinVersion))
    implementation(kotlin("allopen", kotlinVersion))
    implementation(kotlin("noarg", kotlinVersion))
    implementation("gradle.plugin.org.cadixdev.gradle", "licenser", "0.6.1")
}
