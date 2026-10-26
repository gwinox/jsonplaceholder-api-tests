package utils;

import aquality.selenium.browser.AqualityServices;
import com.github.javafaker.Faker;

public class RandomUtils {
    private static final Faker FAKER = new Faker();
    private static final int DEFAULT_STRING_LENGTH = 10;

    private RandomUtils() {
    }

    public static String generateRandomString(int length) {
        String generatedString = FAKER.lorem().characters(length, true, false);
        AqualityServices.getLogger().info(String.format("Generated random string with length %s: %s", length, generatedString));
        return generatedString;
    }

    public static String generateRandomString() {
        return generateRandomString(DEFAULT_STRING_LENGTH);
    }
}