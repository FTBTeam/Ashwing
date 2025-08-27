package dev.ftb.services.ashwing.utils;

/**
 * Local list of supported modloaders.
 */
public enum ModLoader {
    NEOFORGE("NeoForge"),
    FORGE("Forge"),
    FABRIC("Fabric");

    private final String prettyName;
    private final String lowerName;

    ModLoader(String prettyName) {
        this.prettyName = prettyName;
        this.lowerName = prettyName.toLowerCase();
    }

    public String prettyName() {
        return prettyName;
    }

    public String lowerName() {
        return lowerName;
    }

    /**
     * Check if the given string contains any known mod loader names (case-insensitive).
     */
    public static boolean stringIsLoader(String str) {
        if (str == null) return false;
        String lowerStr = str.toLowerCase();
        for (ModLoader loader : ModLoader.values()) {
            if (lowerStr.contains(loader.lowerName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the given string contains any known mod loader names (case-insensitive).
     * <p>
     * This is done in order of enum values. So Neoforge is matched before Forge which avoids
     * false positives.
     */
    public static boolean stringContainsLoader(String str) {
        if (str == null) return false;
        String lowerStr = str.toLowerCase();
        for (ModLoader loader : ModLoader.values()) {
            if (lowerStr.contains(loader.lowerName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a given mod loader is part of the Forge family (Forge or NeoForge).
     */
    public static boolean isForgeFamily(ModLoader loader) {
        return loader == FORGE || loader == NEOFORGE;
    }
}
