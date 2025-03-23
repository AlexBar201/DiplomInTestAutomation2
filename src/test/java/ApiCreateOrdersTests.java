import AndPointAndBaseUri.GetAndSetBaseUri;
import TestData.TestDataGenerator;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import steps.ApiAuthorizationSteps;
import steps.ApiOrdersSteps;

public class ApiCreateOrdersTests {

    ApiAuthorizationSteps authStep = new ApiAuthorizationSteps();
    ApiOrdersSteps ordersStep = new ApiOrdersSteps();
    TestDataGenerator data = new TestDataGenerator();

    @Before
    public void setUp(){
        String configFilePath = "src/main/resources/config.properties";
        GetAndSetBaseUri setBaseUri = new GetAndSetBaseUri(configFilePath);
        setBaseUri.setUp();
    }

    @Test
    public void createAuthOrderTest(){
        Response createUser = authStep.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName());
        String authToken = authStep.getAccessToken(createUser);
        ordersStep.createAuthOrder(authToken).then().statusCode(200);
    }
}
