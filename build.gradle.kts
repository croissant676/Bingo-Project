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
    modules("javafx.controls", "javafx.base", "javafx.graphics", "javafx.swing")
}

repositories {
    mavenCentral()
}

application {
    mainClassName = "dev.kason.bingo.LauncherKt"
}

@Suppress("spellcheckinginspection")
dependencies {
    implementation("org.docx4j:docx4j:3.3.5")
    implementation ("com.sun.xml.bind:jaxb-core:2.3.0.1")
    implementation ("javax.xml.bind:jaxb-api:2.3.1")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.1")
    implementation("org.javassist:javassist:3.25.0-GA")
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
