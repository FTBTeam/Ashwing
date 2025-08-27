package dev.ftb.plugins.ashwing.extension.utils;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.RegularFile;
import org.gradle.api.provider.Provider;
import org.gradle.jvm.tasks.Jar;

public enum Utils {
    INSTANCE;

    /**
     * Creates a NeoForge-like version prefix. Removing the major version and adding
     * a trailing zero if necessary.
     *
     * @param minecraftVersion The Minecraft version, e.g. "1.20.1"
     */
    public String createNeoLikeVersionPrefix(String minecraftVersion) {
        if (isInvalidMinecraftVersion(minecraftVersion)) {
            throw new IllegalArgumentException("Invalid Minecraft version: " + minecraftVersion);
        }

        var versionParts = minecraftVersion.split("\\.");
        var minor = versionParts[1];
        var patch = versionParts.length > 2 ? versionParts[2] : "0";

        return minor + "." + patch;
    }

    /**
     * Creates a FTB-like version prefix. EG: "1.20.1" becomes "2101." This is a compacted
     * version of the minecraft version. We also add in trailing zeros if necessary.
     *
     * @param minecraftVersion The Minecraft version, e.g. "1.20.1"
     */
    public String createFtbVersionPrefix(String minecraftVersion) {
        if (isInvalidMinecraftVersion(minecraftVersion)) {
            throw new IllegalArgumentException("Invalid Minecraft version: " + minecraftVersion);
        }

        var versionParts = minecraftVersion.split("\\.");
        var minor = versionParts[1];
        var patch = versionParts.length > 2 ? versionParts[2] : "0";

        // Pad the minor and patch versions with leading zeros if necessary
        if (minor.length() == 1) {
            minor = "0" + minor;
        }

        if (patch.length() == 1) {
            patch = "0" + patch;
        }

        return minor + patch;
    }

    /**
     * A minecraft version is only valid if it starts with a number, a dot, then another number.
     * It then, can optionally have another dot and number. Anything else is invalid.
     */
    private boolean isInvalidMinecraftVersion(String version) {
        return !version.matches("^\\d+\\.\\d+(\\.\\d+)?$");
    }

    public Provider<RegularFile> getFileFromTask(Project project, String taskName) {
        Task taskFromName = project.getTasks().findByName(taskName);
        if (!(taskFromName instanceof Jar jarTask)) {
            throw new IllegalArgumentException("Task " + taskName + " is not a Jar task!");
        }

        return jarTask.getArchiveFile();
    }
}
