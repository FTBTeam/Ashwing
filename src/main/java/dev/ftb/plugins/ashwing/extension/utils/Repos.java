package dev.ftb.plugins.ashwing.extension.utils;

import dev.ftb.plugins.ashwing.constants.MavenRepositories;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.repositories.MavenRepositoryContentDescriptor;
import org.jetbrains.annotations.Nullable;

/**
 * Loads of helper methods for adding repositories to the project.
 */
@SuppressWarnings("unused")
public record Repos(Project project) {
    //#region FTB Maven
    public void ftb() {
        ftb(false,null);
    }

    public void ftbSnapshots() {
        ftb(true, null);
    }

    public void ftb(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        ftb(false, extraContent);
    }

    public void ftb(boolean snapshots, @Nullable Action<? super MavenRepositoryContentDescriptor> extraContent) {
        var maven = snapshots ? MavenRepositories.FTB_MAVEN_SNAPSHOTS : MavenRepositories.FTB_MAVEN_RELEASES;
        createMaven(maven, extraContent);
    }
    //#endregion

    //#region Mojang Maven
    public void mojang() {
        mojang(null);
    }

    public void mojang(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.MOJANG_MAVEN, extraContent);
    }
    //#endregion

    //#region Forge Maven
    public void forge() {
        forge(null);
    }

    public void forge(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.FORGE_MAVEN, extraContent);
    }
    //#endregion

    //#region Neoforge Maven
    public void neoforge() {
        neoforge(null);
    }

    public void neoforge(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.NEOFORGE_MAVEN, extraContent);
    }
    //#endregion

    //#region Fabric Maven
    public void fabric() {
        fabric(null);
    }

    public void fabric(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.FABRIC_MAVEN, extraContent);
    }
    //#endregion

    //#region CurseMaven
    public void curseMaven() {
        createMaven(MavenRepositories.CURSEMAVEN, null);
    }
    //#endregion

    //#region Modrinth
    public void modrinth() {
        createMaven(MavenRepositories.MODRINTH, null);
    }
    //#endregion

    //#region Common Modding Repositories
    public void architectury() {
        architectury(null);
    }

    public void architectury(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.ARCH_MAVEN, extraContent);
    }

    public void shedaniel() {
        shedaniel(null);
    }

    public void shedaniel(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.SHEDAN_MAVEN, extraContent);
    }

    public void latvian() {
        latvian(null);
    }

    public void latvian(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.LATVIAN, extraContent);
    }

    public void modmaven() {
        modmaven(null);
    }

    public void modmaven(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.MODMAVEN, extraContent);
    }

    public void terraformers() {
        terraformers(null);
    }

    public void terraformers(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.TERRAFORMERS, extraContent);
    }
    //#endregion

    private void createMaven(MavenRepositories mavenRepo, @Nullable Action<? super MavenRepositoryContentDescriptor> extraContent) {
        project.getRepositories().maven(repo -> {
            repo.setUrl(mavenRepo.getUrl());
            repo.setName(mavenRepo.getName());

            repo.mavenContent(mavenContent -> {
                if (extraContent != null) {
                    extraContent.execute(mavenContent);
                }

                for (var include : mavenRepo.getStaticIncludes()) {
                    mavenContent.includeGroup(include);
                }
            });
        });
    }
}
