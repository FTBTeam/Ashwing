package dev.ftb.services.ashwing.extension.publishing;

import me.modmuss50.mpp.PlatformDependency;
import me.modmuss50.mpp.platforms.curseforge.CurseforgeDependency;
import me.modmuss50.mpp.platforms.modrinth.ModrinthDependency;
import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;

import java.util.ArrayList;

public abstract class DependencyList {
    private final Project project;

    public final ListProperty<CurseforgeDependency> curseforgeDependencies;
    public final ListProperty<ModrinthDependency> modrinthDependencies;

    public DependencyList(Project project) {
        this.project = project;
        var objects = project.getObjects();

        this.curseforgeDependencies = objects.listProperty(CurseforgeDependency.class).convention(new ArrayList<>());
        this.modrinthDependencies = objects.listProperty(ModrinthDependency.class).convention(new ArrayList<>());
    }

    public void addModrinthDependency(PlatformDependency.DependencyType type, String slug) {
        var dep = project.getObjects().newInstance(ModrinthDependency.class);
        dep.getSlug().set(slug);
        dep.getType().set(type);
        this.modrinthDependencies.add(dep);
    }

    public void addCurseDependency(PlatformDependency.DependencyType type, String slug) {
        var dep = project.getObjects().newInstance(CurseforgeDependency.class);
        dep.getSlug().set(slug);
        dep.getType().set(type);
        this.curseforgeDependencies.add(dep);
    }
}
