plugins {
    id("stocker.common-conventions")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencyManagement)
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

configurations {
    all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
    }
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation(libs.spring.boot.web)
    implementation(libs.spring.boot.log4j)
    implementation(libs.spring.boot.devtools)
    implementation(libs.spring.boot.configurationProcessor)
    implementation(libs.bundles.exposed)
    implementation(libs.hikari)
    //runtimeOnly(libs.postgresql)

    testImplementation(libs.spring.boot.test)
    runtimeOnly(libs.h2)
}
