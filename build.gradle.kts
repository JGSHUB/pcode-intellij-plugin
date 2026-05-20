import proguard.gradle.ProGuardTask

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.6.1")
    }
}

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.changelog")
    id("org.jetbrains.intellij.platform")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
    intellijPlatform {
        intellijIdea("2025.3.4.1")
        implementation(files("libs/pcode-vm-1.0-obfuscated.jar"))
    }
}

tasks.register<ProGuardTask>("obfuscateJar") {
    injars("libs/pcode-vm-1.0-snapshot.jar")
    outjars("libs/pcode-vm-1.0-obfuscated.jar")
    configuration("proguard-rules.pro")
}

tasks.test {
    useJUnitPlatform()
}