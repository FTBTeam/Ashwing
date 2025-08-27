package dev.ftb.services.ashwing.extension.utils;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.RegularFile;
import org.gradle.api.provider.Provider;
import org.gradle.jvm.tasks.Jar;

public record Utils(Project project) {
    public void createVersionPrefix(String minecraftVersion) {
        System.out.println("Lolipop" + minecraftVersion);
    }

    public Provider<RegularFile> getFileFromTask(Project project, String taskName) {
        Task taskFromName = project.getTasks().findByName(taskName);
        if (!(taskFromName instanceof Jar jarTask)) {
            throw new IllegalArgumentException("Task " + taskName + " is not a Jar task!");
        }

        return jarTask.getArchiveFile();
    }
}
