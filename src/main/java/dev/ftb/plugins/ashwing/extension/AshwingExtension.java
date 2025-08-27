package dev.ftb.plugins.ashwing.extension;

import dev.ftb.plugins.ashwing.extension.publishing.MavenConfigurator;
import dev.ftb.plugins.ashwing.extension.publishing.ModPublisherConfigurator;
import dev.ftb.plugins.ashwing.extension.utils.Changelog;
import dev.ftb.plugins.ashwing.extension.utils.Repos;
import dev.ftb.plugins.ashwing.extension.utils.Utils;
import dev.ftb.plugins.ashwing.utils.Helpers;
import me.modmuss50.mpp.MppPlugin;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

import java.util.Set;

/**
 * Root extension for all things Ashwing.
 */
public class AshwingExtension {
    private final Project project;

    private final AshwingPublishingExtension publishing;
    private boolean isMonoProject = false;

    private final Property<String> modId;
    private final Property<String> modName;

    public AshwingExtension(Project project) {
        this.project = project;

        // Construct various extensions and properties
        ObjectFactory objects = project.getObjects();
        this.publishing = objects.newInstance(AshwingPublishingExtension.class, project, this);

        this.modId = objects.property(String.class);
        this.modName = objects.property(String.class);

        // Figure out if we're a mono project
        // TODO: Actually check the applied plugins in preference of lazy checking
        Set<String> childProjects = this.project.getChildProjects().keySet();
        boolean containsLoaderChildren = false;

        for (String child : childProjects) {
            var childName = child.toLowerCase();
            if (childName.contains("fabric") || childName.contains("forge") || childName.contains("neoforge")) {
                containsLoaderChildren = true;
                break;
            }
        }

        this.isMonoProject = !childProjects.isEmpty() && containsLoaderChildren;

        // Apply the plugin if it's not already applied.
        if (!project.getPlugins().hasPlugin(MppPlugin.class)) {
            project.getPlugins().apply(MppPlugin.class);
        }

        project.afterEvaluate(p -> {
            new MavenConfigurator(p, this).configure();
            new ModPublisherConfigurator(p).configure();
        });
    }

    /**
     * Maven repositories shorthand methods.
     */
    public Repos repos(Project project) {
        return new Repos(project);
    }

    /**
     * General utilities for Ashwing projects.
     */
    public Utils utils() {
        return Utils.INSTANCE;
    }

    /**
     * Configure publishing options.
     */
    public void publishing(Action<AshwingPublishingExtension> action) {
        action.execute(publishing);
    }

    /**
     * Publishing options for this project.
     */
    public AshwingPublishingExtension getPublishing() {
        return publishing;
    }

    /**
     * The root projects mod id.
     */
    public Property<String> getModId() {
        return modId;
    }

    /**
     * The root projects mod name.
     */
    public Property<String> getModName() {
        return modName;
    }

    public String createChangelog() {
        var changelogFile = Helpers.getChangelogFile(this.project);
        return Changelog.INSTANCE.extractCurrentVersionChanges(changelogFile, this.project.getVersion().toString());
    }

    /**
     * If the contained project is a mono repo with multiple modloader children.
     */
    protected boolean isMonoProject() {
        return isMonoProject;
    }
}
