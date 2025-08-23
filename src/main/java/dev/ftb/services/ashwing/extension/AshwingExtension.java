package dev.ftb.services.ashwing.extension;

import dev.ftb.services.ashwing.extension.utils.Repos;
import org.gradle.api.Project;

public class AshwingExtension {
    private final Project project;
    private final Repos repositories;

    public AshwingExtension(final Project project) {
        this.project = project;
        this.repositories = new Repos(project);
    }

    public Repos repos() {
        return repositories;
    }
}
