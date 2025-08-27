package dev.ftb.services.ashwing.utils;

import org.jetbrains.annotations.Nullable;

public class StrHelper {
    public static boolean isNullOrEmpty(@Nullable String str) {
        return str == null || str.isEmpty();
    }

    public static String toTitleCase(String str) {
        if (isNullOrEmpty(str)) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }
}
