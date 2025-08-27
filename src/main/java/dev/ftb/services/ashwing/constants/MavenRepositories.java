package dev.ftb.services.ashwing.constants;

import java.util.List;

public enum MavenRepositories {
    FTB_MAVEN_RELEASES("https://maven.ftb.dev/releases", "FTB Maven", "dev.ftb.mods"),
    FTB_MAVEN_SNAPSHOTS("https://maven.ftb.dev/snapshots", "FTB Maven Snapshots", "dev.ftb.mods"),
    ARCH_MAVEN("https://maven.architectury.dev", "Architectury Maven", "dev.architectury"),
    SHEDAN_MAVEN("https://maven.shedaniel.me", "Shedaniel Maven"),
    MOJANG_MAVEN("https://libraries.minecraft.net", "Mojang Maven"),
    FORGE_MAVEN("https://maven.minecraftforge.net", "Forge Maven"),
    NEOFORGE_MAVEN("https://maven.neoforge.dev", "Neoforge Maven"),
    FABRIC_MAVEN("https://maven.fabricmc.net", "Fabric Maven"),
    CURSEMAVEN("https://maven.cursemaven.com", "CurseMaven", "curse.maven"),
    MODRINTH("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth"),
    LATVIAN("https://maven.latvian.dev", "Latvian Maven", "dev.latvian.mods", "dev.latvian.apps"),
    MODMAVEN("https://modmaven.dev", "ModMaven"),
    TERRAFORMERS("https://maven.terraformersmc.com", "TerraformersMC", "com.terraformersmc"),;

    private final String url;
    private final String name;

    /**
     * Used for repositories that only consist of single include groups.
     * Curseforge and modrinth are examples of this.
     */
    private final List<String> staticIncludes;

    MavenRepositories(String url, String name) {
        this.url = url;
        this.name = name;
        this.staticIncludes = List.of();
    }

    MavenRepositories(String url, String name, String... staticIncludes) {
        this.url = url;
        this.name = name;
        this.staticIncludes = List.of(staticIncludes);
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public List<String> getStaticIncludes() {
        return staticIncludes;
    }
}
