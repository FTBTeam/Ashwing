package dev.ftb.plugins.ashwing.extensions.utils;


import dev.ftb.plugins.ashwing.extension.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilsTest {
    private static final List<String> invalidInputs = List.of(
        "1",
        "1.",
        "1.20.",
        "abc",
        "1.20.a",
        "version1.20.1",
        ""
    );

    @Test
    void neoLikeVersionCreatesCorrectly() {
        var inputVersions = Map.of(
                "1.20.1", "20.1",
                "1.19.4", "19.4",
                "1.20", "20.0"
        );

        for (var entry : inputVersions.entrySet()) {
            var input = entry.getKey();
            var expected = entry.getValue();

            var actual = Utils.INSTANCE.createNeoLikeVersionPrefix(input);
            assertEquals(expected, actual);
        }
    }

    @Test
    void neoLikeVersionThrowsOnInvalidInput() {
        for (var input : invalidInputs) {
            assertThrows(IllegalArgumentException.class, () -> Utils.INSTANCE.createNeoLikeVersionPrefix(input), "Provided input: " + input + " did not throw an exception" );
        }
    }

    @Test
    void ftbVersionCreatesCorrectly() {
        var inputVersions = Map.of(
                "1.20.1", "2001",
                "1.19.4", "1904",
                "1.20", "2000",
                "1.21.8", "2108"
        );

        for (var entry : inputVersions.entrySet()) {
            var input = entry.getKey();
            var expected = entry.getValue();

            var actual = Utils.INSTANCE.createFtbVersionPrefix(input);
            assertEquals(expected, actual);
        }
    }

    @Test
    void ftbVersionThrowsOnInvalidInput() {
        for (var input : invalidInputs) {
            assertThrows(IllegalArgumentException.class, () -> Utils.INSTANCE.createFtbVersionPrefix(input));
        }
    }
}
