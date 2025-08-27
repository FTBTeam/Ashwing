package dev.ftb.services.ashwing.extension.publishing;

import dev.ftb.services.ashwing.extension.AshwingExtension;
import dev.ftb.services.ashwing.extension.AshwingPublishingExtension;
import dev.ftb.services.ashwing.utils.Helpers;
import dev.ftb.services.ashwing.utils.StrHelper;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;

public record MavenConfigurator(Project project) {
    /**
     * Setups the FTB Maven as a publishing target.
     */
    public void configure() {
        AshwingPublishingExtension publishing = project.getExtensions().getByType(AshwingExtension.class).getPublishing();
        MavenOptions maven = publishing.maven();
        if (!maven.getUsername().isPresent() && !maven.getPassword().isPresent()) {
            return;
        }

        var mavenUsername = maven.getUsername().getOrNull();
        var mavenPassword = maven.getPassword().getOrNull();

        if (StrHelper.isNullOrEmpty(mavenUsername) || StrHelper.isNullOrEmpty(mavenPassword)) {
            throw new IllegalStateException("Maven publishing is enabled, but no username or password was provided.");
        }

        Helpers.getSelfProjectOrLoaderChildren(this.project)
                .forEach(e -> applyMavenPublications(e, mavenUsername, mavenPassword));
    }

    // TODO: Expand the pom details to contain FTB Details
    // TODO: Sign the publication with the ENV signing key (Maybe)
    public void applyMavenPublications(Project theProject, String mavenUsername, String mavenPassword) {
        theProject.getPlugins().withType(JavaPlugin.class, javaPlugin -> {
            // Check if the publishing extension is already applied
            if (!theProject.getPlugins().hasPlugin(MavenPublishPlugin.class)) {
                theProject.getPlugins().apply(MavenPublishPlugin.class);
            }

            PublishingExtension publishingExtension = theProject.getExtensions().getByType(PublishingExtension.class);
            publishingExtension.repositories(config -> config.maven(mavenRepo -> {
                mavenRepo.setUrl("https://maven.ftb.dev");
                mavenRepo.credentials(credentials -> {
                    credentials.setUsername(mavenUsername);
                    credentials.setPassword(mavenPassword);
                });
            }));

            publishingExtension.publications(publications -> {
                var publicationName = "ashwingMaven" + theProject.getName();
                MavenPublication publication = (MavenPublication) publications.findByName(publicationName);
                if (publication == null) {
                    // This creates it AND adds it.
                    publications.create(publicationName, MavenPublication.class, config -> {
                        config.from(theProject.getComponents().getByName("java"));

                        // TODO: Allow user input for the task names
                        // Find if we have a sources jar
                        var sourceJar = theProject.getTasks().findByName("sourcesJar");
                        if (sourceJar != null) {
                            config.artifact(sourceJar);
                        }

                        // Find if we have a javadoc jar
                        var javadocJar = theProject.getTasks().findByName("javadocJar");
                        if (javadocJar != null) {
                            config.artifact(javadocJar);
                        }

                        config.pom(pomConfig -> {
                            // TODO: Fill out the pom details
                        });
                    });
                }
            });
        });
    }
}
