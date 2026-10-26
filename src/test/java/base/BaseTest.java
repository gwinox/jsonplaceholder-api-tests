package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import utils.ConfigManager;

public abstract class BaseTest {

    @BeforeClass
    public void restGetUrl() {
        RestAssured.baseURI = ConfigManager.getUrl();
    }
}