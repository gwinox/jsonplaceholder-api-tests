package utils;

import models.users.User;
import java.io.File;

public class TestDataManager {
    private static final String EXPECTED_USER_FILE_PATH = new File("src/test/resources/expectedUser.json").getAbsolutePath();

    private TestDataManager() {
    }

    public static User getExpectedUser() {
        return JsonUtils.deserializeJson(EXPECTED_USER_FILE_PATH, User.class);
    }
}