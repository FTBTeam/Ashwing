# Ashwing

Ashwing is an opinionated gradle plugin to help standardize FTB mod development environments.

We provide a curated set of tasks and extensions to produce consistent outputs across all FTB mods.

Ashwing is provided in an `as-is` state and is under constant development. We welcome contributions and feedback but ultimately, we use this primarily for our own projects and this plugin is subject to change at any time.

## Usage

```groovy
// Find the latest version at https://maven.ftb.dev/#/releases/dev/ftb/plugins/ashwing/

plugins {
    id 'dev.ftb.plugins.ashwing' version '$VERSION'
}
```

## Features

- Automatically handles publishing to maven, Curseforge and Modrinth when configured
- Update changelogs with `UNRELEASED` sections
- Create NeoForge like, and FTB like version prefixes
- Provides `shorthand` declarations of common maven repositories

_More to come_

### Todos

- Support signing of jars before publishing

## Usage

Gradle should provide auto-completion for our extensions but here is a vague example of how you might configure Ashwing

```groovy
ashwing {
    modId = "ftblibrary"
    modName = "FTB Library"

    publishing {
        // You can add more than one minecraft version to target multiple versions
        minecraftVersion(minecraft_version)
        
        // This will configure the version type, aka, stable, beta, alpha. It will figure it out based on the input if it contains, beta, alpha or omits it for stable
        releaseFromString(providers.environmentVariable("TAG").getOrElse("release"))

        // When tokens are provided, this will publish, otherwise, dryrun
        curseForgeToken = providers.environmentVariable("CURSEFORGE_TOKEN").orElse("")
        modrinthToken = providers.environmentVariable("MODRINTH_TOKEN").orElse("")

        maven {
            username = "ftb"
            password = "Hello"
        }

        // These can be set above when they are the same for all projects.
        // curseforgeId = 123456
        // modrinthId = "abcd"
        
        // Shared dependencies between platforms as they share the same slug
        required("architectury-api")
        optional("jei")
        optional("roughly-enough-items")

        // NeoForge setup
        modProject {
            curseforgeId = curseforge_id_forge.toInteger()
            modLoader = "neoforge"
            // Helper to pull the output file from a specified task
            fileFromTask(project(":neoforge"), "remapJar")
        }

        // Fabric setup
        modProject {
            curseforgeId = curseforge_id_fabric.toInteger()
            modLoader = "fabric"
            fileFromTask(project(":fabric"), "remapJar")
        }
    }
}
```

_Subject to change_