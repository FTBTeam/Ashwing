package dev.ftb.services.ashwing;

import dev.ftb.services.ashwing.extension.AshwingExtension;
import dev.ftb.services.ashwing.extension.ModInfoExtension;
import dev.ftb.services.ashwing.tasks.CreateInfoTasks;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

public class AshwingPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        ExtensionContainer extensions = project.getExtensions();
        TaskContainer tasks = project.getTasks();

        // Extensions
        extensions.create("ashwing", AshwingExtension.class, project);
        extensions.create("modInfo", ModInfoExtension.class, project);

        // Tasks
        tasks.register("createInfoTasks", CreateInfoTasks.class);
    }
}
