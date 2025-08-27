package dev.ftb.plugins.ashwing.utils;

import org.jetbrains.annotations.Nullable;

public class StrHelper {
    /**
     * Checks if a string is null or empty.
     *
     * @param str The string to check.
     * @return True if the string is null or empty, false otherwise.
     */
    public static boolean isNullOrEmpty(@Nullable String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Converts a string to title case.
     *
     * @param str The string to convert.
     * @return The string in title case.
     */
    public static String toTitleCase(String str) {
        if (isNullOrEmpty(str)) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    /**
     * Converts a string to a safe case for URLs or filenames.
     * Replaces all non-alphanumeric characters with hyphens and converts to lowercase.
     *
     * @param str The string to convert.
     * @return The string in safe case.
     */
    public static String toSafeCase(String str) {
        if (isNullOrEmpty(str)) return str;
        return str.toLowerCase().replaceAll("[^a-z0-9]+", "-");
    }
}
