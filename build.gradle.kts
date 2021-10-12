plugins {
    kotlin("multiplatform") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.31"
}

group = "com.gabrielleeg1"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.nukkitx.com/snapshot")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("reflect"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.5.2")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

                implementation("com.nukkitx.network:query:1.6.28-SNAPSHOT")
                implementation("com.nukkitx.network:raknet:1.6.28-SNAPSHOT")
                implementation("com.nukkitx.network:rcon:1.6.28-SNAPSHOT")
                implementation("com.nukkitx:natives:1.0.3")
            }
        }
        val jvmTest by getting
    }
}
