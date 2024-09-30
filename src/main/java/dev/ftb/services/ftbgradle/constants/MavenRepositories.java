package dev.ftb.services.ftbgradle.constants;

import org.gradle.internal.impldep.com.google.common.collect.ImmutableList;

import java.util.List;


public enum MavenRepositories {
    FTB_MAVEN_RELEASES("https://maven.ftb.dev/releases", "FTB Maven"),
    FTB_MAVEN_SNAPSHOTS("https://maven.ftb.dev/snapshots", "FTB Maven Snapshots"),
    SAPS_MAVEN_RELEASES("https://maven.saps.dev/releases", "SAPS Maven"),
    SAPS_MAVEN_SNAPSHOTS("https://maven.saps.dev/snapshots", "SAPS Maven Snapshots"),
    NANITE_MAVEN_RELEASES("https://maven.nanite.dev/releases", "Nanite Maven"),
    NANITE_MAVEN_SNAPSHOTS("https://maven.nanite.dev/snapshots", "Nanite Maven Snapshots"),
    ARCH_MAVEN("https://maven.architectury.dev", "Architectury Maven"),
    SHEDAN_MAVEN("https://maven.shedaniel.me", "Shedaniel Maven"),
    MOJANG_MAVEN("https://libraries.minecraft.net", "Mojang Maven"),
    FORGE_MAVEN("https://maven.minecraftforge.net", "Forge Maven"),
    NEOFORGE_MAVEN("https://maven.neoforge.dev", "Neoforge Maven"),
    FABRIC_MAVEN("https://maven.fabricmc.net", "Fabric Maven"),
    QUILT_MAVEN("https://maven.quiltmc.org", "Quilt Maven"),
    CURSEMAVEN("https://maven.cursemaven.com", "CurseMaven", "curse.maven"),
    MODRINTH("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth"),
    CREEPERHOST("https://maven.creeper.host", "CreeperHost");

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
