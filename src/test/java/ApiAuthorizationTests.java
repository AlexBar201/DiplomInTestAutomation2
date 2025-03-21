import config.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import steps.ApiAuthorizationSteps;

public class ApiAuthorizationTests {
    private static String baseUri;

    @Before
    public void setUp(){
        String configFilePath = "src/main/resources/config.properties";
        Config config = new Config(configFilePath);
        baseUri = config.getBaseUri();
        RestAssured.baseURI = baseUri;
    }

    @Test
    public void createUserTest(){
        ApiAuthorizationSteps step = new ApiAuthorizationSteps();
        Response response = step.createUser();
        response.then().statusCode(200);
    }
}
