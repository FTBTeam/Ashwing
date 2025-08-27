package dev.ftb.plugins.ashwing;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Roughly based on @Modmuss50's code from <a href="https://github.com/modmuss50/mod-publish-plugin/blob/main/src/test/kotlin/me/modmuss50/mpp/test/IntegrationTest.kt">mod-publish-plugin</a>
 */
public class IntegrationTest {
     public TestBuilder testBuilder() {
         return new TestBuilder();
     }

    public static class TestBuilder {
         private final GradleRunner runner = GradleRunner.create()
                 .withPluginClasspath()
                 .forwardOutput()
                 .withDebug(true);

        private final Path gradleHome;
        private final Path projectDir;
        private final Path buildScript;
        private String buildScriptContents;
        private final List<String> arguments = new ArrayList<>();

        public TestBuilder() {
            var testDirectory = Path.of("build/integrationTest");

            this.gradleHome = testDirectory.resolve("gradleHome");
            this.projectDir = testDirectory.resolve("project");
            this.buildScript = projectDir.resolve("build.gradle");

            if (Files.notExists(this.projectDir) && !this.projectDir.toFile().mkdirs()) {
                throw new RuntimeException("Failed to create project directory");
            }

            if (Files.notExists(this.gradleHome) && !this.gradleHome.toFile().mkdirs()) {
                throw new RuntimeException("Failed to create gradle home directory");
            }

            // Clean up
            Stream.of(projectDir.resolve("build.gradle"), projectDir.resolve("settings.gradle"))
                    .filter(path -> path.toFile().exists())
                    .forEach(path -> path.toFile().delete());

            buildScriptContents = """
                    plugins {
                        id 'java'
                        id 'dev.ftb.services.ashwing'
                    }
                    """.stripIndent();

            try {
                Files.writeString(projectDir.resolve("settings.gradle"), "rootProject.name = \"ftb-example\"");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            runner.withProjectDir(projectDir.toFile());
            this.argument("--gradle-user-home", gradleHome.toAbsolutePath().toString())
                    .argument("--stacktrace")
                    .argument("--warning-mode", "fail")
                    .argument("clean");
        }

        public TestBuilder argument(String... args) {
            arguments.addAll(List.of(args));
            return this;
        }

        public TestBuilder buildScript(String script) {
            this.buildScriptContents += "\n" + script;
            return this;
        }

        public BuildResult run(String task) {
            // Write the build script
            try {
                Files.writeString(buildScript, buildScriptContents);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            argument(task);
            runner.withArguments(arguments);
            return runner.run();
        }
    }
}
