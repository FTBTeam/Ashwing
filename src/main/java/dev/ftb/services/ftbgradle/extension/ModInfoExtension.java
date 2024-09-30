package dev.ftb.services.ftbgradle.extension;

import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public abstract class ModInfoExtension {
    private final Project project;

    @Input
    Property<String> id;

    @Input
    Property<String> name;

    @Input
    Property<Object> version;

    @Input
    Property<String> description;

    @Input
    ListProperty<String> authors;

    @Input
    Property<String> license;

    @Input
    Property<String> issueTracker;

    @Input
    Property<String> website;

    @Input
    MapProperty<String, String[]> fabricEntrypoints;

    @Input
    ListProperty<String> mixins;

    public ModInfoExtension(final Project project) {
        this.project = project;

        // Not the best defaults
        ObjectFactory objects = project.getObjects();
        this.id = objects.property(String.class).convention(project.getName());
        this.name = objects.property(String.class).convention(project.getName());
        this.version = objects.property(Object.class).convention(project.getVersion());

        this.authors = objects.listProperty(String.class).convention(List.of("FTB Team"));
        this.license = objects.property(String.class).convention("All Rights Reserved");

        this.issueTracker = objects.property(String.class).convention("https://go.ftb.team/support-mod-issues");
        this.website = objects.property(String.class).convention("https://feed-the-beast.com");

        this.fabricEntrypoints = objects.mapProperty(String.class, String[].class).convention(Map.of());
        this.mixins = objects.listProperty(String.class).convention(List.of());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ModInfoExtension.class.getSimpleName() + "[", "]")
                .add("project=" + project)
                .add("id=" + id)
                .add("name=" + name)
                .add("version=" + version)
                .add("description=" + description)
                .add("authors=" + authors)
                .add("license=" + license)
                .add("issueTracker=" + issueTracker)
                .add("website=" + website)
                .add("fabricEntrypoints=" + fabricEntrypoints)
                .add("mixins=" + mixins)
                .toString();
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
