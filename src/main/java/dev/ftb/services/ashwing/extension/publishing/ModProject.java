package dev.ftb.services.ashwing.extension.publishing;

import dev.ftb.services.ashwing.extension.AshwingExtension;
import me.modmuss50.mpp.PlatformDependency;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

public abstract class ModProject extends DependencyList {
    private final Property<String> modLoader;
    private final RegularFileProperty file;
    private final ConfigurableFileCollection additionalFiles;

    /**
     * Overrides the modrinthId provided in the publishing extension
     */
    private final Property<String> modrinthId;

    /**
     * Overrides the curseforgeId provided in the publishing extension
     */
    private final Property<Integer> curseforgeId;

    @Inject
    public ModProject(Project project) {
        super(project);

        ObjectFactory objects = project.getObjects();
        this.modrinthId = objects.property(String.class);
        this.curseforgeId = objects.property(Integer.class);

        this.modLoader = objects.property(String.class);
        this.file = objects.fileProperty();
        this.additionalFiles = objects.fileCollection();
    }

    public Property<String> getModrinthId() {
        return modrinthId;
    }

    public Property<Integer> getCurseforgeId() {
        return curseforgeId;
    }

    public Property<String> getModLoader() {
        return modLoader;
    }

    public RegularFileProperty getFile() {
        return file;
    }

    public ConfigurableFileCollection getAdditionalFiles() {
        return additionalFiles;
    }

    public void fileFromTask(Project project, String taskName) {
        project.afterEvaluate(innerProject -> {
            var ashwing = innerProject.getRootProject().getExtensions().getByType(AshwingExtension.class);
            this.file.set(ashwing.utils().getFileFromTask(innerProject, taskName).get());
        });
    }

    public void additionalFileFromTask(Project project, String taskName) {
        var ashwing = project.getRootProject().getExtensions().getByType(AshwingExtension.class);
        this.additionalFiles.from(ashwing.utils().getFileFromTask(project, taskName).get());
    }

    public void requireCurse(String slug) {
        addCurseDependency(PlatformDependency.DependencyType.REQUIRED, slug);
    }

    public void optionalCurse(String slug) {
        addCurseDependency(PlatformDependency.DependencyType.OPTIONAL, slug);
    }

    public void incompatibleCurse(String slug) {
        addCurseDependency(PlatformDependency.DependencyType.INCOMPATIBLE, slug);
    }

    public void embeddedCurse(String slug) {
        addCurseDependency(PlatformDependency.DependencyType.EMBEDDED, slug);
    }

    public void requireModrinth(String slug) {
        addModrinthDependency(PlatformDependency.DependencyType.REQUIRED, slug);
    }

    public void optionalModrinth(String slug) {
        addModrinthDependency(PlatformDependency.DependencyType.OPTIONAL, slug);
    }

    public void incompatibleModrinth(String slug) {
        addModrinthDependency(PlatformDependency.DependencyType.INCOMPATIBLE, slug);
    }

    public void embeddedModrinth(String slug) {
        addModrinthDependency(PlatformDependency.DependencyType.EMBEDDED, slug);
    }
}