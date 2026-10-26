package utils;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class ConfigManager {

    private static final ISettingsFile SETTINGS = new JsonSettingsFile("settings.json");

    private ConfigManager() {
    }

    public static String getUrl() {
        return getString("/baseUrl");
    }

    public static String getString(String jsonPath) {
        String value = SETTINGS.getValue(jsonPath).toString();
        AqualityServices.getLogger().info(String.format("Get string value from config by path '%s': %s", jsonPath, value));
        return value;
    }

    public static int getInt(String jsonPath) {
        int value = (int) SETTINGS.getValue(jsonPath);
        AqualityServices.getLogger().info(String.format("Get int value from config by path '%s': %s", jsonPath, value));
        return value;
    }
}