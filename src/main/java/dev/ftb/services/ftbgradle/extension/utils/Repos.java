package dev.ftb.services.ftbgradle.extension.utils;

import dev.ftb.services.ftbgradle.constants.MavenRepositories;
import org.gradle.api.Project;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.artifacts.repositories.RepositoryContentDescriptor;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Loads of helper methods for adding repositories to the project.
 */
public class Repos {
    private final Project project;

    public Repos(Project project) {
        this.project = project;
    }

    //#region FTB Maven
    public MavenArtifactRepository ftb() {
        return ftb(false, null);
    }

    public MavenArtifactRepository ftb(boolean snapshots) {
        return ftb(snapshots, null);
    }

    public MavenArtifactRepository ftb(Consumer<RepositoryContentDescriptor> extraContent) {
        return ftb(false, extraContent);
    }

    public MavenArtifactRepository ftb(boolean snapshots, @Nullable Consumer<RepositoryContentDescriptor> extraContent) {
        var maven = snapshots ? MavenRepositories.FTB_MAVEN_SNAPSHOTS : MavenRepositories.FTB_MAVEN_RELEASES;
        return createMaven(maven.getUrl(), maven.getName(), extraContent);
    }
    //#endregion

    //#region SAPS Maven
    public MavenArtifactRepository saps() {
        return saps(false, null);
    }

    public MavenArtifactRepository saps(boolean snapshots) {
        return saps(snapshots, null);
    }

    public MavenArtifactRepository saps(Consumer<RepositoryContentDescriptor> extraContent) {
        return saps(false, extraContent);
    }

    public MavenArtifactRepository saps(boolean snapshots, @Nullable Consumer<RepositoryContentDescriptor> extraContent) {
        var maven = snapshots ? MavenRepositories.SAPS_MAVEN_SNAPSHOTS : MavenRepositories.SAPS_MAVEN_RELEASES;
        return createMaven(maven.getUrl(), maven.getName(), extraContent);
    }
    //#endregion

    //#region Architectury Maven
    public MavenArtifactRepository architectury() {
        return architectury(null);
    }

    public MavenArtifactRepository architectury(Consumer<RepositoryContentDescriptor> extraContent) {
        return createMaven(MavenRepositories.ARCH_MAVEN.getUrl(), MavenRepositories.ARCH_MAVEN.getName(), extraContent);
    }
    //#endregion

    //#region Shedaniel Maven
    public MavenArtifactRepository shedaniel() {
        return shedaniel(null);
    }

    public MavenArtifactRepository shedaniel(Consumer<RepositoryContentDescriptor> extraContent) {
        return createMaven(MavenRepositories.SHEDAN_MAVEN.getUrl(), MavenRepositories.SHEDAN_MAVEN.getName(), extraContent);
    }
    //#endregion

    //#region Mojang Maven
    public MavenArtifactRepository mojang() {
        return mojang(null);
    }

    public MavenArtifactRepository mojang(Consumer<RepositoryContentDescriptor> extraContent) {
        return createMaven(MavenRepositories.MOJANG_MAVEN.getUrl(), MavenRepositories.MOJANG_MAVEN.getName(), extraContent);
    }
    //#endregion

    //#region Forge Maven
    public MavenArtifactRepository forge() {
        return forge(null);
    }

    public MavenArtifactRepository forge(Consumer<RepositoryContentDescriptor> extraContent) {
        return createMaven(MavenRepositories.FORGE_MAVEN.getUrl(), MavenRepositories.FORGE_MAVEN.getName(), extraContent);
    }
    //#endregion

    //#region Neoforge Maven
    public MavenArtifactRepository neoforge() {
        return neoforge(null);
    }

    public MavenArtifactRepository neoforge(Consumer<RepositoryContentDescriptor> extraContent) {
        return createMaven(MavenRepositories.NEOFORGE_MAVEN.getUrl(), MavenRepositories.NEOFORGE_MAVEN.getName(), extraContent);
    }
    //#endregion

    //#region Fabric Maven
    public MavenArtifactRepository fabric() {
        return fabric(null);
    }

    public MavenArtifactRepository fabric(Consumer<RepositoryContentDescriptor> extraContent) {
        return createMaven(MavenRepositories.FABRIC_MAVEN.getUrl(), MavenRepositories.FABRIC_MAVEN.getName(), extraContent);
    }
    //#endregion

    //#region Quilt Maven
    public MavenArtifactRepository quilt() {
        return quilt(null);
    }

    public MavenArtifactRepository quilt(Consumer<RepositoryContentDescriptor> extraContent) {
        return createMaven(MavenRepositories.QUILT_MAVEN.getUrl(), MavenRepositories.QUILT_MAVEN.getName(), extraContent);
    }
    //#endregion

    //#region CurseMaven
    public MavenArtifactRepository curseMaven() {
        return createMaven(MavenRepositories.CURSEMAVEN.getUrl(), MavenRepositories.CURSEMAVEN.getName(), content -> {
            MavenRepositories.CURSEMAVEN.getStaticIncludes().forEach(content::includeGroup);
        });
    }
    //#endregion

    //#region Modrinth
    public MavenArtifactRepository modrinth() {
        return createMaven(MavenRepositories.MODRINTH.getUrl(), MavenRepositories.MODRINTH.getName(), contents -> {
            MavenRepositories.MODRINTH.getStaticIncludes().forEach(contents::includeGroup);
        });
    }
    //#endregion

    //#region CreeperHost
    public MavenArtifactRepository creeperHost() {
        return creeperHost(null);
    }

    public MavenArtifactRepository creeperHost(Consumer<RepositoryContentDescriptor> extraContent) {
        return createMaven(MavenRepositories.CREEPERHOST.getUrl(), MavenRepositories.CREEPERHOST.getName(), extraContent);
    }
    //#endregion

    private MavenArtifactRepository createMaven(String url, String name, @Nullable Consumer<RepositoryContentDescriptor> extraContent) {
        return project.getRepositories().maven(repo -> {
            repo.setUrl(url);
            repo.setName(name);
            repo.content(content -> {
                if (extraContent != null) {
                    extraContent.accept(content);
                }
            });
        });
    }
}
