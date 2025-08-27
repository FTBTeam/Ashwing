package dev.ftb.plugins.ashwing.extensions;

import dev.ftb.plugins.ashwing.IntegrationTest;
import dev.ftb.plugins.ashwing.constants.MavenRepositories;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.Test;

public class AshwingExtensionTest extends IntegrationTest {
    @Test
    void injectsFTBMavenAndSnapshots() {
        var result = testBuilder()
                .buildScript("""
                        repositories {
                            ashwing.repos(project).ftb()
                            ashwing.repos(project).ftbSnapshots()
                        }
                        
                        tasks.register("testRepos") {
                            doLast {
                                // Check if the FTB Maven repository is present, if not, fail the build
                                if (!repositories.any { it.name == "%s" }) {
                                    throw new IllegalStateException("FTB Maven repository not found")
                                }
                        
                                // Check if the FTB Maven Snapshots repository is present, if not, fail the build
                                if (!repositories.any { it.name == "%s" }) {
                                    throw new IllegalStateException("FTB Maven Snapshots repository not found")
                                }
                            }
                        }
                        """.formatted(MavenRepositories.FTB_MAVEN_RELEASES.getName(), MavenRepositories.FTB_MAVEN_SNAPSHOTS.getName()))
                .run("testRepos");

        BuildTask task = result.task(":testRepos");

        assert task != null;
        assert task.getOutcome() == TaskOutcome.SUCCESS;
    }
}
