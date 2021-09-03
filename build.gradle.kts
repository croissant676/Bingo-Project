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
    implementation("javax.xml.bind:jaxb-api:2.1")
    implementation("com.itextpdf:itextpdf:5.0.6")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test-junit"))
}

@Suppress("spellcheckinginspection")
sourceSets {
    main {
        java {
            exclude("org.bouncycastle/mail.smime.*")
        }
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
