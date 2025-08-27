package dev.ftb.services.ashwing.extension.records;

import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Options for publishing to various platforms.
 */
public class PublisherOptions {
    /**
     * Enables maven publishing.
     */
    public boolean enableMaven = true;

    /**
     * Enables CurseForge publishing.
     */
    public boolean enableCurseForge = false;

    /**
     * Enables Modrinth publishing.
     */
    public boolean enableModrinth = false;

    /**
     * Required when enableCurseForge is true.
     */
    @Nullable public String curseToken = null;

    /**
     * Required when enableModrinth is true.
     */
    @Nullable public String modrinthToken = null;

    /**
     * Required when enableMaven is true.
     */
    @Nullable public String mavenUsername = null;

    /**
     * Required when enableMaven is true.
     */
    @Nullable public String mavenPassword = null;

    public boolean autoGenerateChangelog = true;
    @Nullable public File changelogFile = null;
    @Nullable public String changelogText = null;

    @Nullable public String modrinthId = null;
    @Nullable public Integer curseforgeId = null;

    /**
     * The fabric / forge / neoforge modrinth are used as overrides.
     */
    @Nullable public String overrideForgeModrinthId = null;
    @Nullable public String overrideFabricModrinthId = null;
    @Nullable public String overrideNeoForgeModrinthId = null;

    /**
     * The fabric / forge / neoforge curseforge are used as overrides.
     */
    @Nullable public Integer overrideForgeCurseforgeId = null;
    @Nullable public Integer overrideFabricCurseforgeId = null;
    @Nullable public Integer overrideNeoForgeCurseforgeId = null;
}

/**
 * publishMods {
 *     dryRun = providers.environmentVariable("CURSEFORGE_KEY").getOrNull() == null
 *     changelog = createChangelog(project)
 *     version = mod_version
 *
 *     // TODO: Migrate to something else
 *     def tag = providers.environmentVariable("TAG").getOrElse("release")
 *     type = tag.endsWith("-beta") ? BETA : (tag.endsWith("-alpha") ? ALPHA : STABLE)
 *
 *     def createOptions = (String projectName) -> {
 *         publishOptions {
 *             file = project.provider { project(":$projectName").tasks.remapJar }.flatMap { it.archiveFile }
 *             displayName = "[${projectName.toUpperCase()}][${minecraft_version}] ${readable_name} ${mod_version}"
 *             modLoaders.add(projectName.toLowerCase())
 *         }
 *     }
 *
 *     def fabricOptions = createOptions("fabric")
 *     def neoForgeOptions = createOptions("neoforge")
 * //	def forgeOptions = createOptions("forge")
 *
 *     def curseForgeOptions = curseforgeOptions {
 *         accessToken = providers.environmentVariable("CURSEFORGE_KEY")
 *         minecraftVersions.add("${minecraft_version}")
 *         javaVersions.add(JavaVersion.VERSION_21)
 *     }
 *
 *     curseforge("curseforgeFabric") {
 *         from(curseForgeOptions, fabricOptions)
 *         projectId = curseforge_id_fabric
 *         requires('architectury-api')
 *         requires('fabric-api')
 *         optional('jei')
 *         optional('roughly-enough-items')
 *     }
 *
 *     curseforge("curseforgeNeoForge") {
 *         from(curseForgeOptions, neoForgeOptions)
 *         projectId = curseforge_id_forge
 *         requires("architectury-api")
 *         optional('jei')
 *         optional('roughly-enough-items')
 *     }
 *
 * //	curseforge("curseforgeForge") {
 * //		from(curseForgeOptions, forgeOptions)
 * //		projectId = curseforge_id_forge
 * //		requires("architectury-api")
 * //		optional('jei')
 * //		optional('roughly-enough-items')
 * //	}
 * }
 */