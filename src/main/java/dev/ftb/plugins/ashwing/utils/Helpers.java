package dev.ftb.plugins.ashwing.utils;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginContainer;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helpers {
    private static final String CHANGELOG_FILE = "CHANGELOG.md";

    public static File getChangelogFile(Project project) {
        Project rootProject = project.getRootProject();
        File file = rootProject.file("./" + CHANGELOG_FILE);
        if (!file.exists()) {
            throw new RuntimeException("Changelog file not found: " + file.getAbsolutePath());
        }

        return file;
    }

    public static List<Project> getSelfProjectOrLoaderChildren(Project rootProject) {
        var childProjects = rootProject.getChildProjects().values();
        boolean containsLoaderChildren = childProjects.stream().anyMatch(child -> ModLoader.stringContainsLoader(child.getName()));

        if (!childProjects.isEmpty() && containsLoaderChildren) {
            return childProjects.stream().toList();
        } else {
            return List.of(rootProject);
        }
    }

    /**
     * Resolves each modloader like project back to it's toolchain and project.
     */
    public static Map<ModLoader, ImmutablePair<Toolchain, Project>> gatherProjectsByLoader(Project aProject) {
        var projects = getSelfProjectOrLoaderChildren(aProject.getRootProject());

        Map<ModLoader, ImmutablePair<Toolchain, Project>> projectByLoader = new HashMap<>();
        for (var project : projects) {
            // We could determine the loader by the project name. But really, we should check it's plugins
            PluginContainer plugins = project.getPlugins();

            for (Toolchain toolchain : Toolchain.values()) {
                if (plugins.hasPlugin(toolchain.pluginId())) {
                    var loader = toolchain.loaderProvider().apply(project);
                    projectByLoader.put(loader, ImmutablePair.of(toolchain, project));
                }
            }
        }

        return projectByLoader;
    }

    public static JavaVersion getJavaVersionFromProject(Project project) {
        if (!project.hasProperty("sourceCompatibility")) {
            throw new RuntimeException("Project does not have a sourceCompatibility property!");
        }

        return (JavaVersion) project.findProperty("sourceCompatibility");
    }
}
