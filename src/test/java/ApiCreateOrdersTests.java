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
    @DisplayName("Проверка создания заказа с авторизацией")
    @Description("Авторизованный пользователь может создать заказ, возвращается статус код \"200\"")
    public void createAuthOrderTest(){
        Response createUser = authStep.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName());
        String authToken = authStep.getAccessToken(createUser);
        ordersStep.createAuthOrder(authToken)
                .then()
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка создания заказа без авторизации")
    @Description("Не авторизованный пользователь может создать заказ, возвращается статус код \"200\"")
    public void createNoAuthOrderTest(){
        ordersStep.createNoAuthOrder()
                .then()
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка создания заказа без ингредиентов")
    @Description("Если не передать ни один ингредиент, вернется статус код \"400\"")
    public void createAuthOrderNoIngredientsTest(){
        Response createUser = authStep.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName());
        String authToken = authStep.getAccessToken(createUser);
        ordersStep.createAuthOrderNoIngredients(authToken)
                .then()
                .statusCode(400)
                .assertThat()
                .body("success", equalTo(false));
        authStep.deleteUser(createUser);
    }

    @Test
    @DisplayName("Проверка создания заказа с неверным хешем ингредиентов")
    @Description("Если передать невалидный хеш ингредиентов, вернется статус код \"500\"")
    public void createAuthOrderInvalidHashTest(){
        Response createUser = authStep.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName());
        String authToken = authStep.getAccessToken(createUser);
        ordersStep.createAuthOrderInvalidHash(authToken)
                .then()
                .statusCode(500);
        authStep.deleteUser(createUser);
    }

    @After
    public void tearDown(){
        authStep.deleteUser(authStep.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName()))
                .then()
                .statusCode(202);
    }
}
