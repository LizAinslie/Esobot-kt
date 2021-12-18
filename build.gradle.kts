plugins {
    kotlin("jvm") version "1.6.10"
}

group = "dev.lizainslie"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("net.dv8tion:JDA:5.0.0-alpha.2") {
        exclude(module = "opus-java")
    }
}