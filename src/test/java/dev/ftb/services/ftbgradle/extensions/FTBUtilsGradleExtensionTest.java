package dev.ftb.services.ftbgradle.extensions;

import dev.ftb.services.ftbgradle.IntegrationTest;
import dev.ftb.services.ftbgradle.constants.MavenRepositories;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.Test;

public class FTBUtilsGradleExtensionTest extends IntegrationTest {
    @Test
    void injectsFTBMavenAndSnapshots() {
        var result = testBuilder()
                .buildScript("""
                        repositories {
                            ftbUtils.repos().ftb()
                            ftbUtils.repos().ftb(true) // Snapshots
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
