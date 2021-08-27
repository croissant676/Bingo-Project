plugins {
    kotlin("jvm") version "1.4.32"
    id("org.openjfx.javafxplugin") version "0.0.10"
    application
}

@Suppress("spellcheckinginspection")
group = "dev.kason"
version = "1.0-SNAPSHOT"

javafx {
    version = "14"
    modules("javafx.controls", "javafx.fxml", "javafx.base", "javafx.graphics", "javafx.media", "javafx.web")
}
repositories {
    mavenCentral()
}

application {
    mainClassName = "com.example.MainKt"
}

@Suppress("spellcheckinginspection")
dependencies {
    implementation("com.itextpdf:itextpdf:5.0.6")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test-junit"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}