package dev.ftb.services.ashwing.utils;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskContainer;

import java.util.function.Function;

public enum Toolchain {
    LOOM("loom", "fabric-loom", (container) -> container.findByName("remapJar"), (project) -> null),
    // TODO: These tasks are likely wrong!
    FORGE("forge", "net.minecraftforge.gradle", (container) -> container.findByName("reobfJar"), (project) -> ModLoader.FORGE),
    NEOFORGE("neoforge", "net.neoforged.gradle.userdev", (container) -> container.findByName("reobfJar"), (project) -> ModLoader.NEOFORGE),
    NEOFORGE_MOD_DEV("neoforge_moddev", "net.neoforged.moddev", (container) -> container.findByName("reobfJar"), (project) -> null),
    ARCH_LOOM("arch_loom", "dev.architectury.loom", (container) -> container.findByName("remapJar"), (project) -> null);

    private final String id;
    private final String pluginId;
    private final Function<TaskContainer, Task> buildTaskProvider;
    private final Function<Project, ModLoader> loaderProvider;

    Toolchain(String id, String pluginId, Function<TaskContainer, Task> buildTaskProvider, Function<Project, ModLoader> loaderProvider) {
        this.id = id;
        this.pluginId = pluginId;
        this.buildTaskProvider = buildTaskProvider;
        this.loaderProvider = loaderProvider;
    }

    public String id() {
        return id;
    }

    public String pluginId() {
        return pluginId;
    }

    public Function<TaskContainer, Task> buildTaskProvider() {
        return buildTaskProvider;
    }

    public Function<Project, ModLoader> loaderProvider() {
        return loaderProvider;
    }
}
