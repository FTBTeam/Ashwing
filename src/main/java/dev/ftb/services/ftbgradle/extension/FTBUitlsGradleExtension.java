package dev.ftb.services.ftbgradle.extension;

import dev.ftb.services.ftbgradle.extension.utils.Repos;
import org.gradle.api.Project;

public class FTBUitlsGradleExtension {
    private final Project project;
    private final Repos repositories;

    public FTBUitlsGradleExtension(final Project project) {
        this.project = project;
        this.repositories = new Repos(project);
    }

    public Repos repos() {
        return repositories;
    }


}
