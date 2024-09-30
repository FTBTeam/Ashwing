package dev.ftb.services.ftbgradle.tasks;

import dev.ftb.services.ftbgradle.extension.ModInfoExtension;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class CreateInfoTasks extends DefaultTask {
    public CreateInfoTasks() {
        setGroup("ftb");
        setDescription("Creates the tasks for the mod info");
    }

    @TaskAction
    public void createInfoTasks() {
        // Get the ModInfo data
        ModInfoExtension modInfo = getProject().getExtensions().getByType(ModInfoExtension.class);

        System.out.println("Mod Info:");
        System.out.println(modInfo);

        // Get project dependencies
        var dependencies = getProject().getConfigurations().getByName("implementation").getDependencies();
        System.out.println("Dependencies:");
        dependencies.forEach(System.out::println);
    }

    private void generateFabricInfo() {
        // JSON
    }

    private void generateForgeInfo() {
        // TOML
    }

    private void generateNeoforgeInfo() {
        // TOML
    }

    private void generateMixinsInfo() {
        // JSON
    }
}
