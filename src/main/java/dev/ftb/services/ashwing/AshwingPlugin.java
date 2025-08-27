package dev.ftb.services.ashwing;

import dev.ftb.services.ashwing.extension.AshwingExtension;
import dev.ftb.services.ashwing.tasks.PrepareChangelog;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

@SuppressWarnings("unused")
public class AshwingPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        // Tasks
        TaskContainer tasks = project.getTasks();
        tasks.register("prepareChangelog", PrepareChangelog.class);

        // Extensions
        ExtensionContainer extensions = project.getExtensions();
        extensions.create("ashwing", AshwingExtension.class, project);
    }
}
