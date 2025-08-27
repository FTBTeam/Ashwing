package dev.ftb.services.ashwing.tasks;

import dev.ftb.services.ashwing.extension.utils.Changelog;
import dev.ftb.services.ashwing.utils.Helpers;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Files;

/**
 * Takes in the changelog file and prepares it for release.
 * Finds the first [Unreleased] section and renames it to the current version,
 * then adds a new [Unreleased] section at the top.
 */
public class PrepareChangelog extends DefaultTask {
    public PrepareChangelog() {
        setGroup("ashwing");
        setDescription("Prepares the changelog for a new release.");
    }

    @TaskAction
    public void prepare() {
        var file = Helpers.getChangelogFile(this.getProject());

        String version = this.getProject().getRootProject().getVersion().toString();
        String updatedContent = Changelog.INSTANCE.createUpdatedChangelogContent(file, version);

        try {
            Files.writeString(file.toPath(), updatedContent);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write changelog file: " + file.getAbsolutePath(), e);
        }
    }
}
