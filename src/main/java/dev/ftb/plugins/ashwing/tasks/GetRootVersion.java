package dev.ftb.plugins.ashwing.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class GetRootVersion extends DefaultTask {
    public GetRootVersion() {
        setGroup("ashwing");
        setDescription("Gets the root project version.");
    }

    @TaskAction
    public void getVersion() {
        String version = this.getProject().getRootProject().getVersion().toString();
        System.out.println("projectVersion=" + version);
    }
}
