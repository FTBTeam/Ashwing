package dev.ftb.plugins.ashwing.extension.publishing;

import dev.ftb.plugins.ashwing.extension.AshwingExtension;
import dev.ftb.plugins.ashwing.extension.AshwingPublishingExtension;
import dev.ftb.plugins.ashwing.utils.Helpers;
import dev.ftb.plugins.ashwing.utils.StrHelper;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.gradle.jvm.tasks.Jar;

import java.util.Map;

public record MavenConfigurator(Project project, AshwingExtension ashwingExtension) {
    /**
     * Setups the FTB Maven as a publishing target.
     */
    public void configure() {
        AshwingPublishingExtension publishing = ashwingExtension.getPublishing();
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
                .forEach(e -> e.afterEvaluate(theProject -> applyMavenPublications(theProject, mavenUsername, mavenPassword, publishing)));
    }

    public void applyMavenPublications(Project theProject, String mavenUsername, String mavenPassword, AshwingPublishingExtension ashwingPublishing) {
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
                var publicationName = "ashwingMaven" + StrHelper.toTitleCase(theProject.getName());
                MavenPublication publication = (MavenPublication) publications.findByName(publicationName);
                if (publication == null) {
                    // This creates it AND adds it.
                    publications.create(publicationName, MavenPublication.class, config -> {
                        config.setArtifactId(StrHelper.toSafeCase(this.ashwingExtension.getModName().get() + "-" + theProject.getName()));
                        config.from(theProject.getComponents().getByName("java"));

                        addArtifact(theProject, config, "javadocJar", "javadoc");
                        addArtifact(theProject, config, "apiJar", "api");

                        MapProperty<String, String> additionalArtifacts = ashwingPublishing.maven().getAdditionalArtifacts();
                        if (additionalArtifacts.isPresent()) {
                            additionalArtifacts.get().forEach((taskName, classifier) -> addArtifact(theProject, config, taskName, classifier));
                        }

                        config.pom(pomConfig -> {
                            pomConfig.getName().set(ashwingExtension.getModName());
                            pomConfig.getProperties().set(Map.of(
                                    "modId", ashwingExtension.getModId().get()
                            ));
                            pomConfig.issueManagement(issueConfig -> {
                                issueConfig.getUrl().set("https://go.ftb.team/support-mod-issues");
                                issueConfig.getSystem().set("Github Issues");
                            });
                            pomConfig.licenses(licenses -> licenses.license(license -> {
                                license.getName().set("ARR");
                                license.getComments().set("Sources are provided as-is. All rights reserved to the original Feed The Beast LTD. Unless explicitly stated otherwise.");
                                license.getUrl().set("https://go.ftb.team/mod-license");
                            }));
                            pomConfig.developers(devs -> devs.developer(dev -> {
                                dev.getName().set("FTB Team");
                                dev.getEmail().set("admin@feed-the-beast.com");
                                dev.getOrganization().set("FTB");
                                dev.getOrganizationUrl().set("https://feed-the-beast.com");
                                dev.getRoles().addAll("Owner", "Developer", "Maintainer");
                            }));
                        });
                    });
                }
            });
        });
    }

    private void addArtifact(Project theProject, MavenPublication publication, String taskName, String classifier) {
        var task = theProject.getTasks().findByName(taskName);
        if (task == null) {
            return;
        }

        if (!(task instanceof Jar jarTask)) {
            throw new IllegalStateException("Task " + taskName + " is not a Jar task.");
        }

        publication.artifact(jarTask, artifactConfig -> {
            artifactConfig.setClassifier(classifier);
        });
    }
}
