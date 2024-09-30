package dev.ftb.services.ftbgradle.tasks;

import dev.ftb.services.ftbgradle.IntegrationTest;
import org.junit.jupiter.api.Test;

public class CreateInfoTaskTest extends IntegrationTest {
    @Test
    public void testCreateInfoTask() {
        var result = testBuilder()
                .buildScript("""
                        modInfo {
                            name = "Testing mod"
                        }
                        
                        # Add a dependency to test the task
                        dependencies {
                            implementation "org.apache.commons:commons-lang3:3.12.0"
                        }
                        """)
                .run("createInfoTasks");

        var task = result.task(":createInfoTasks");
        System.out.println(task);
        assert task != null;
    }
}
