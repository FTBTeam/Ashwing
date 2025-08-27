package dev.ftb.plugins.ashwing.extension.utils;

import java.io.File;
import java.nio.file.Files;

public enum Changelog {
    INSTANCE;

    private static final String UNRELEASED_MARKER = "## [Unreleased]";

    public String extractCurrentVersionChanges(File changelogFile, String version) {
        String content = readContents(changelogFile);

        String startMarker = "## [" + version + "]";
        StringBuilder versionContent = new StringBuilder();
        boolean inVersionSection = false;

        for (String line : content.split("\n")) {
            if (line.startsWith("## [") && line.contains(startMarker)) {
                inVersionSection = true;
                continue;
            }

            if (inVersionSection) {
                if (line.startsWith("## [")) {
                    break;
                }

                versionContent.append(line).append("\n");
            }
        }

        String result = versionContent.toString().trim();
        if (result.isEmpty()) {
            return "No changes recorded.";
        }

        return result;
    }

    /**
     * Creates a new changelog content with the updated version a new Unreleased section.
     *
     * @param changelogFile The changelog file to read.
     * @param version The version to set for the unreleased section.
     *
     * @return The updated changelog content.
     */
    public String createUpdatedChangelogContent(File changelogFile, String version) {
        String content = readContents(changelogFile);

        if (!content.contains(UNRELEASED_MARKER)) {
            throw new RuntimeException("Changelog file does not contain an 'Unreleased' section.");
        }

        var newContent = new StringBuilder();
        var unreleasedUpdated = false;
        for (var line : content.split("\n")) {
            if (!unreleasedUpdated && line.startsWith("## [") && line.contains(UNRELEASED_MARKER)) {
                unreleasedUpdated = true;

                line = line.replace(UNRELEASED_MARKER, "## [" + version + "]");
                newContent.append("## [Unreleased]\n\n");
                newContent.append("### Changed\n\n");
                newContent.append(line).append("\n");
                continue;
            }

            newContent.append(line).append("\n");
        }

        return newContent.toString().trim() + "\n";
    }

    private String readContents(File file) {
        try {
            return Files.readString(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException("Failed to read changelog file: " + file.getAbsolutePath(), e);
        }
    }
}
