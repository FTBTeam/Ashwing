package dev.ftb.services.ashwing.extension;

import dev.ftb.services.ashwing.extension.utils.Repos;
import dev.ftb.services.ashwing.extension.utils.Shared;
import org.gradle.api.Project;

public class AshwingExtension {
    private final Project project;

    private final Repos repositories;
    private final Shared shared;

    public AshwingExtension(final Project project) {
        this.project = project;

        this.repositories = new Repos(project);
        this.shared = new Shared(project);
    }

    public Repos repos() {
        return repositories;
    }

    public Shared shared() {
        return shared;
    }
}
