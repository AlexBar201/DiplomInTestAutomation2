import AndPointAndBaseUri.GetAndSetBaseUri;
import TestData.TestDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
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
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
        authStep.deleteUser(createUser);
    }

    @Test
    @DisplayName("Проверка создания заказа без авторизации")
    @Description("Не авторизованный пользователь может создать заказ, возвращается статус код \"200\"")
    public void createNoAuthOrderTest(){
        ordersStep.createNoAuthOrder()
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверка создания заказа без ингредиентов")
    @Description("Если не передать ни один ингредиент, вернется статус код \"400\"")
    public void createAuthOrderNoIngredientsTest(){
        Response createUser = authStep.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName());
        String authToken = authStep.getAccessToken(createUser);
        ordersStep.createAuthOrderNoIngredients(authToken)
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(400);
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

    @Test
    @DisplayName("Проверка получения заказа авторизованного пользователя")
    @Description("Если авторизованный пользователь запросит заказ, вернется статус код \"200\"")
    public void getAuthUserOrderTest(){
        Response createUser = authStep.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName());
        String authToken = authStep.getAccessToken(createUser);
        ordersStep.getAuthUserOrder(authToken)
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
        authStep.deleteUser(createUser);
    }

    @Test
    @DisplayName("Проверка получения заказа не авторизованного пользователя")
    @Description("Если не авторизованный пользователь запросит заказ, вернется статус код \"401\"")
    public void getNoAuthUserOrderTest(){
        ordersStep.getNoAuthUserOrder()
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(401);
    }
}
