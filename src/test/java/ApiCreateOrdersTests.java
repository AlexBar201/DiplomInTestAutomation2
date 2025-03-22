import AndPointAndBaseUri.GetAndSetBaseUri;
import TestData.TestDataGenerator;
import org.junit.Before;
import org.junit.Test;
import steps.ApiOrdersSteps;

public class ApiCreateOrdersTests {

    ApiOrdersSteps step = new ApiOrdersSteps();
    TestDataGenerator data = new TestDataGenerator();

    @Before
    public void setUp(){
        String configFilePath = "src/main/resources/config.properties";
        GetAndSetBaseUri setBaseUri = new GetAndSetBaseUri(configFilePath);
        setBaseUri.setUp();
    }

    @Test
    public void createAuthOrderTest(){
        step.createAuthOrder(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName()).then().statusCode(200);
    }
}
