plugins {
    id("java")
    id("com.gradle.plugin-publish") version "1.2.0"
    `java-gradle-plugin`
}

val isSnapshot = providers.environmentVariable("SNAPSHOT").getOrElse("false").toBoolean()

group = "dev.ftb.services"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType(JavaCompile::class.java).all {
    options.release = 21
}

gradlePlugin {
    website = "https://github.com/ftbteam/Ashwing"
    vcsUrl = "https://github.com/ftbteam/Ashwing"

    plugins.create("ashwing") {
        id = "dev.ftb.services.ashwing"
        implementationClass = "dev.ftb.services.ashwing.AshwingPlugin"
        displayName = "Ashwing"
        description = "Tools for FTB Mods"
        version = project.version
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories {
        val token = providers.environmentVariable("FTB_MAVEN")
        if (token.isPresent) {
            maven {
                url = uri("https://maven.ftb.dev/${if (isSnapshot) "snapshots" else "releases"}")
                credentials {
                    username = "ftb"
                    password = token.get()
                }
            }
        }
    }
}
