package dev.ftb.plugins.ashwing.extension.publishing;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

public abstract class MavenOptions {
    private final Property<String> username;
    private final Property<String> password;
    private final MapProperty<String, String> additionalArtifacts;

    @Inject
    public MavenOptions(ObjectFactory objects) {
        this.username = objects.property(String.class);
        this.password = objects.property(String.class);
        this.additionalArtifacts = objects.mapProperty(String.class, String.class);
    }

    public Property<String> getUsername() {
        return username;
    }

    public Property<String> getPassword() {
        return password;
    }

    public MapProperty<String, String> getAdditionalArtifacts() {
        return additionalArtifacts;
    }
}
