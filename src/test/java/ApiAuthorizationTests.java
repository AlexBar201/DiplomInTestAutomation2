import AndPointAndBaseUri.GetAndSetBaseUri;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import steps.ApiAuthorizationSteps;

public class ApiAuthorizationTests {

    @Before
    public void setUp(){
        String configFilePath = "src/main/resources/config.properties";
        GetAndSetBaseUri setBaseUri = new GetAndSetBaseUri(configFilePath);
        setBaseUri.setUp();
    }

    @Test
    public void createUserTest(){
        ApiAuthorizationSteps step = new ApiAuthorizationSteps();
        Response response = step.createUser();
        response.then().statusCode(200);
    }
}
