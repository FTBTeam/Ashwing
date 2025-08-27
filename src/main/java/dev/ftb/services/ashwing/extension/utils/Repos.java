package dev.ftb.services.ashwing.extension.utils;

import dev.ftb.services.ashwing.constants.MavenRepositories;
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
        createMaven(maven.getUrl(), maven.getName(), extraContent);
    }
    //#endregion

    //#region Mojang Maven
    public void mojang() {
        mojang(null);
    }

    public void mojang(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.MOJANG_MAVEN.getUrl(), MavenRepositories.MOJANG_MAVEN.getName(), extraContent);
    }
    //#endregion

    //#region Forge Maven
    public void forge() {
        forge(null);
    }

    public void forge(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.FORGE_MAVEN.getUrl(), MavenRepositories.FORGE_MAVEN.getName(), extraContent);
    }
    //#endregion

    //#region Neoforge Maven
    public void neoforge() {
        neoforge(null);
    }

    public void neoforge(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.NEOFORGE_MAVEN.getUrl(), MavenRepositories.NEOFORGE_MAVEN.getName(), extraContent);
    }
    //#endregion

    //#region Fabric Maven
    public void fabric() {
        fabric(null);
    }

    public void fabric(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.FABRIC_MAVEN.getUrl(), MavenRepositories.FABRIC_MAVEN.getName(), extraContent);
    }
    //#endregion

    //#region CurseMaven
    public void curseMaven() {
        createMaven(MavenRepositories.CURSEMAVEN.getUrl(), MavenRepositories.CURSEMAVEN.getName(), content -> {
            MavenRepositories.CURSEMAVEN.getStaticIncludes().forEach(content::includeGroup);
        });
    }
    //#endregion

    //#region Modrinth
    public void modrinth() {
        createMaven(MavenRepositories.MODRINTH.getUrl(), MavenRepositories.MODRINTH.getName(), contents -> {
            MavenRepositories.MODRINTH.getStaticIncludes().forEach(contents::includeGroup);
        });
    }
    //#endregion

    //#region Common Modding Repositories
    public void architectury() {
        architectury(null);
    }

    public void architectury(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.ARCH_MAVEN.getUrl(), MavenRepositories.ARCH_MAVEN.getName(), extraContent);
    }

    public void shedaniel() {
        shedaniel(null);
    }

    public void shedaniel(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.SHEDAN_MAVEN.getUrl(), MavenRepositories.SHEDAN_MAVEN.getName(), extraContent);
    }

    public void latvian() {
        latvian(null);
    }

    public void latvian(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.LATVIAN.getUrl(), MavenRepositories.LATVIAN.getName(), extraContent);
    }

    public void modmaven() {
        modmaven(null);
    }

    public void modmaven(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.MODMAVEN.getUrl(), MavenRepositories.MODMAVEN.getName(), extraContent);
    }

    public void terraformers() {
        terraformers(null);
    }

    public void terraformers(Action<? super MavenRepositoryContentDescriptor> extraContent) {
        createMaven(MavenRepositories.TERRAFORMERS.getUrl(), MavenRepositories.TERRAFORMERS.getName(), extraContent);
    }
    //#endregion

    private void createMaven(String url, String name, @Nullable Action<? super MavenRepositoryContentDescriptor> extraContent) {
        project.getRepositories().maven(repo -> {
            repo.setUrl(url);
            repo.setName(name);
            if (extraContent != null) {
                repo.mavenContent(extraContent);
            }
        });
    }
}
