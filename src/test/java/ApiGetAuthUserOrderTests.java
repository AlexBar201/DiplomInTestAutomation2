import and.point.and.base.uri.GetAndSetBaseUri;
import data.TestDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.ApiAuthorizationSteps;
import steps.ApiOrdersSteps;

import static org.hamcrest.Matchers.equalTo;

public class ApiGetAuthUserOrderTests {

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
    @DisplayName("Проверка получения заказа авторизованного пользователя")
    @Description("Если авторизованный пользователь запросит заказ, вернется статус код \"200\"")
    public void getAuthUserOrderTest(){
        Response createUser = authStep.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName());
        String authToken = authStep.getAccessToken(createUser);
        ordersStep.getAuthUserOrder(authToken)
                .then()
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
        authStep.deleteUser(createUser);
    }

    @Test
    @DisplayName("Проверка получения заказа не авторизованного пользователя")
    @Description("Если не авторизованный пользователь запросит заказ, вернется статус код \"401\"")
    public void getNoAuthUserOrderTest(){
        ordersStep.getNoAuthUserOrder()
                .then()
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false));
    }

    @After
    public void tearDown(){
        authStep.deleteUser(authStep.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName()))
                .then()
                .statusCode(202);
    }
}
