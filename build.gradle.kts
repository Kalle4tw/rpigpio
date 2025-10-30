



plugins {
    kotlin("jvm") version "2.0.20"
    id("com.vanniktech.maven.publish") version "0.34.0"
}



group = "io.github.kalle4tw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}


mavenPublishing {
    coordinates("io.github.kalle4tw", "rpigpio", "1.0.0")

    pom {
        name.set("Raspberry Pi GPIO for Kotlin")
        description.set("A lightweight, easy-to-use Kotlin library for GPIO control on Raspberry Pi with native performance. Supports all Raspberry Pi models (Pi 1-5, Zero, Zero W).")
        inceptionYear.set("2025")
        url.set("https://github.com/kalle4tw/rpigpio/")
        licenses {
            license {
                name.set("The MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("kalle4tw")
                name.set("Kalle4tw")
                url.set("https://github.com/kalle4tw/")
            }
        }
        scm {
            url.set("https://github.com/kalle4tw/rpigpio/")
            connection.set("scm:git:git://github.com/kalle4tw/rpigpio.git")
            developerConnection.set("scm:git:ssh://git@github.com/kalle4tw/rpigpio.git")
        }
    }
}

signing {
    useGpgCmd()
}