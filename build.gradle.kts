plugins {
    id("java")
    id("com.gradle.plugin-publish") version "1.2.0"
    `java-gradle-plugin`
}

val isSnapshot = providers.environmentVariable("SNAPSHOT").getOrElse("false").toBoolean()

group = "dev.ftb.plugins"
version = "0.1.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // Use modmuss50's plugin to help with publishing
    implementation("me.modmuss50:mod-publish-plugin:0.8.4")

    // Commons lang3
    implementation("org.apache.commons:commons-lang3:3.18.0")

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
        id = "dev.ftb.plugins.ashwing"
        implementationClass = "dev.ftb.plugins.ashwing.AshwingPlugin"
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
