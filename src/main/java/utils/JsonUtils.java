package utils;


import aquality.selenium.browser.AqualityServices;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    public static <T> T deserializeJson(String filepath, Class<T> tClass) {

        AqualityServices.getLogger().info("Trying to read file");
        String absolutePath = new File(filepath).getAbsolutePath();

        try {
            return OBJECT_MAPPER.readValue(new File(absolutePath), tClass);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Cannot read file: %s", e.getMessage()));
        }

    }
}