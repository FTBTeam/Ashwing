package dev.ftb.plugins.ashwing.extension;

import dev.ftb.plugins.ashwing.extension.publishing.DependencyList;
import dev.ftb.plugins.ashwing.extension.publishing.MavenOptions;
import dev.ftb.plugins.ashwing.extension.publishing.ModProject;
import me.modmuss50.mpp.PlatformDependency;
import me.modmuss50.mpp.ReleaseType;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class AshwingPublishingExtension extends DependencyList {
    private final Project project;
    private final AshwingExtension ashwingExtension;

    private final MavenOptions maven;

    private final Property<ReleaseType> releaseType;

    private final Property<String> modrinthId;
    private final Property<Integer> curseforgeId;

    private final Property<String> curseForgeToken;
    private final Property<String> modrinthToken;

    private final List<String> minecraftVersions = new ArrayList<>();
    private final List<ModProject> modProjects = new ArrayList<>();

    @Inject
    public AshwingPublishingExtension(Project project, AshwingExtension ashwingExtension) {
        super(project);

        this.project = project;
        this.ashwingExtension = ashwingExtension;

        ObjectFactory objects = project.getObjects();

        this.maven = objects.newInstance(MavenOptions.class);
        this.releaseType = objects.property(ReleaseType.class).convention(ReleaseType.STABLE);
        this.curseForgeToken = objects.property(String.class);
        this.modrinthToken = objects.property(String.class);
        this.modrinthId = objects.property(String.class);
        this.curseforgeId = objects.property(Integer.class);
    }

    public void maven(Action<? super MavenOptions> action) {
        action.execute(maven);
    }

    public MavenOptions maven() {
        return maven;
    }

    public void minecraftVersion(String version) {
        this.minecraftVersions.add(version);
    }

    public List<String> minecraftVersions() {
        return minecraftVersions;
    }

    public void modProject(Action<? super ModProject> action) {
        var modProject = project.getObjects().newInstance(ModProject.class, this.project);
        action.execute(modProject);
        this.modProjects.add(modProject);
    }

    public List<ModProject> projects() {
        return this.modProjects;
    }

    public ReleaseType getReleaseType() {
        return releaseType.get();
    }

    public Property<String> getCurseForgeToken() {
        return curseForgeToken;
    }

    public Property<String> getModrinthToken() {
        return modrinthToken;
    }

    public void releaseFromString(String input) {
        var holder = input.toLowerCase();

        if (holder.contains("beta")) {
            releaseType.set(ReleaseType.BETA);
        } else if (holder.contains("alpha")) {
            releaseType.set(ReleaseType.ALPHA);
        } else {
            releaseType.set(ReleaseType.STABLE);
        }
    }

    public Property<String> getModrinthId() {
        return modrinthId;
    }

    public Property<Integer> getCurseforgeId() {
        return curseforgeId;
    }

    public void required(String slug) {
        addModrinthDependency(PlatformDependency.DependencyType.REQUIRED, slug);
        addCurseDependency(PlatformDependency.DependencyType.REQUIRED, slug);
    }

    public void optional(String slug) {
        addModrinthDependency(PlatformDependency.DependencyType.OPTIONAL, slug);
        addCurseDependency(PlatformDependency.DependencyType.OPTIONAL, slug);
    }

    public void incompatible(String slug) {
        addModrinthDependency(PlatformDependency.DependencyType.INCOMPATIBLE, slug);
        addCurseDependency(PlatformDependency.DependencyType.INCOMPATIBLE, slug);
    }

    public void embedded(String slug) {
        addModrinthDependency(PlatformDependency.DependencyType.EMBEDDED, slug);
        addCurseDependency(PlatformDependency.DependencyType.EMBEDDED, slug);
    }
}
