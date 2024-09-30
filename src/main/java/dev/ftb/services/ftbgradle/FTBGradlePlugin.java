package dev.ftb.services.ftbgradle;

import dev.ftb.services.ftbgradle.extension.FTBUitlsGradleExtension;
import dev.ftb.services.ftbgradle.extension.ModInfoExtension;
import dev.ftb.services.ftbgradle.tasks.CreateInfoTasks;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class FTBGradlePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getExtensions().create("ftbUtils", FTBUitlsGradleExtension.class, project);
        project.getExtensions().create("modInfo", ModInfoExtension.class, project);

        project.getTasks().register("createInfoTasks", CreateInfoTasks.class);
    }
}
