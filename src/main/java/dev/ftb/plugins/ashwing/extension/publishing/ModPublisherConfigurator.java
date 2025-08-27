package dev.ftb.plugins.ashwing.extension.publishing;

import dev.ftb.plugins.ashwing.extension.AshwingExtension;
import dev.ftb.plugins.ashwing.extension.AshwingPublishingExtension;
import dev.ftb.plugins.ashwing.utils.Helpers;
import dev.ftb.plugins.ashwing.utils.StrHelper;
import me.modmuss50.mpp.ModPublishExtension;
import me.modmuss50.mpp.PublishOptions;
import me.modmuss50.mpp.platforms.curseforge.CurseforgeDependency;
import me.modmuss50.mpp.platforms.modrinth.ModrinthDependency;
import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;

import java.util.ArrayList;
import java.util.List;

public record ModPublisherConfigurator(Project project) {
    public void configure() {
        AshwingExtension ashwing = project.getExtensions().getByType(AshwingExtension.class);
        AshwingPublishingExtension publishing = ashwing.getPublishing();
        List<ModProject> projects = publishing.projects();

        if (projects.isEmpty()) {
            System.out.println("No mod projects found, skipping mod publishing setup.");
            return;
        }

        var mppExtension = project.getExtensions().getByType(ModPublishExtension.class);

        var curseToken = publishing.getCurseForgeToken();
        var modrinthToken = publishing.getModrinthToken();

        mppExtension.getDryRun().set(!curseToken.isPresent() && !modrinthToken.isPresent());
        mppExtension.getType().set(publishing.getReleaseType());
        mppExtension.getChangelog().set(ashwing.createChangelog());

        var javaVersion = Helpers.getJavaVersionFromProject(project);

        var curseDependencies = publishing.curseforgeDependencies;
        var modrinthDependencies = publishing.modrinthDependencies;

        var primaryModrinthId = publishing.getModrinthId().getOrNull();
        var primaryCurseforgeId = publishing.getCurseforgeId().getOrNull();

        for (var modProject : projects) {
            var modrinthId = getOverrideOrDefault(modProject.getModrinthId().getOrNull(), primaryModrinthId);
            var curseforgeId = getOverrideOrDefault(modProject.getCurseforgeId().getOrNull(), primaryCurseforgeId);

            String modLoader = StrHelper.toTitleCase(modProject.getModLoader().get());
            String displayName = "[%s] %s %s".formatted(modLoader, ashwing.getModName().get(), this.project.getVersion());

            if (curseforgeId != null) {
                mppExtension.curseforge("curseForge" + modLoader + curseforgeId, config -> {
                    applyToBaseOptions(config, modProject, displayName, modLoader);

                    config.getProjectId().set(String.valueOf(curseforgeId));
                    if (curseToken.isPresent()) {
                        config.getAccessToken().set(curseToken.get());
                    }
                    ListProperty<String> minecraftVersions = config.getMinecraftVersions();
                    for (String version : publishing.minecraftVersions()) {
                        minecraftVersions.add(version);
                    }
                    config.getJavaVersions().set(List.of(javaVersion));

                    List<CurseforgeDependency> joinedDependencies = new ArrayList<>(curseDependencies.get());
                    joinedDependencies.addAll(modProject.curseforgeDependencies.get());
                    curseDependencies.set(joinedDependencies);
                });
            }

            if (modrinthId != null) {
                mppExtension.modrinth("modrinth" + modLoader + modrinthId, config -> {
                    if (modrinthToken.isPresent()) {
                        config.getProjectId().set(modrinthId);
                    }
                    config.getAccessToken().set(modrinthToken.get());

                    applyToBaseOptions(config, modProject, displayName, modLoader);

                    ListProperty<String> minecraftVersions = config.getMinecraftVersions();
                    for (String version : publishing.minecraftVersions()) {
                        minecraftVersions.add(version);
                    }

                    List<ModrinthDependency> joinedDependencies = new ArrayList<>(modrinthDependencies.get());
                    joinedDependencies.addAll(modProject.modrinthDependencies.get());
                    modrinthDependencies.set(joinedDependencies);
                });
            }
        }
    }

    private void applyToBaseOptions(PublishOptions options, ModProject modProject, String displayName, String modLoader) {
        options.getDisplayName().set(displayName);
        options.getFile().set(modProject.getFile());
        options.getModLoaders().set(List.of(modLoader));
        for (var additionalFile : modProject.getAdditionalFiles()) {
            options.getAdditionalFiles().from(additionalFile);
        }
    }

    private <T> T getOverrideOrDefault(T override, T defaultValue) {
        return override != null ? override : defaultValue;
    }
}
