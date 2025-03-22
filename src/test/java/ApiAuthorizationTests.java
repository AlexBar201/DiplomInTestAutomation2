import AndPointAndBaseUri.GetAndSetBaseUri;
import TestData.TestDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.ApiAuthorizationSteps;

import static org.hamcrest.Matchers.equalTo;

public class ApiAuthorizationTests {

    ApiAuthorizationSteps step = new ApiAuthorizationSteps();
    TestDataGenerator data = new TestDataGenerator();

    @Before
    public void setUp(){
        String configFilePath = "src/main/resources/config.properties";
        GetAndSetBaseUri setBaseUri = new GetAndSetBaseUri(configFilePath);
        setBaseUri.setUp();
    }

    @Test
    @DisplayName("Проверка на создание уникального пользователя")
    @Description("При создании уникального пользователя возвращается статус код \"200\"")
    public void createUniqueUserTest(){
        step.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName())
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверка, что нельзя создать уже существующего пользователя")
    @Description("При попытке создать уже существующего пользователя возвращается статус код \"403 Forbidden\"")
    public void createExistingUserTest(){
        step.createExistingUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName())
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(403);
    }

    @Test
    @DisplayName("Проверка, что нельзя создать пользователя не передав обязательное поле \"email\"")
    @Description("Если не передать одно из обязательных полей возвращается ошибка \"403 Forbidden\"")
    public void createUniqueUserNoFieldEmailTest(){
        step.createUserEmptyEmail(data.getRandomPassword(), data.getRandomName())
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(403);
    }

    @Test
    @DisplayName("Проверка, что нельзя создать пользователя не передав обязательное поле \"password\"")
    @Description("Если не передать одно из обязательных полей возвращается ошибка \"403 Forbidden\"")
    public void createUniqueUserNoFieldPasswordTest(){
        step.createUserEmptyPassword(data.getRandomEmail(), data.getRandomName())
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(403);
    }

    @Test
    @DisplayName("Проверка, что нельзя создать пользователя не передав обязательное поле \"name\"")
    @Description("Если не передать одно из обязательных полей возвращается ошибка \"403 Forbidden\"")
    public void createUniqueUserNoFieldNameTest(){
        step.createUserEmptyName(data.getRandomEmail(), data.getRandomPassword())
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(403);
    }

    @Test
    @DisplayName("Проверка, что можно авторизоваться, под существующим логином и правильным паролем")
    @Description("Если передать корректные поля вернется статус код \"200\"")
    public void authorizationExistingUserTest(){
        step.authorizationExistingUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName())
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверка, что нельзя авторизоваться, под не существующим логином и правильным паролем")
    @Description("Если передать некорректный логин вернется статус код \"401 Unauthorized\"")
    public void authorizationInvalidEmailUserTest(){
        step.authorizationInvalidEmail(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName(), data.getRandomInvalidEmail())
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(401);
    }

    @Test
    @DisplayName("Проверка, что нельзя авторизоваться, под существующим логином и не правильным паролем")
    @Description("Если передать некорректный пароль вернется статус код \"401 Unauthorized\"")
    public void authorizationInvalidPasswordUserTest(){
        step.authorizationInvalidPassword(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName(), data.getRandomInvalidPassword())
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(401);
    }

    @Test
    @DisplayName("Проверка, что можно изменить поле \"email\", если пользователь авторизован")
    @Description("Если пользователь авторизован и поле успешно изменено вернется статус код \"200\"")
    public void changeEmailForAuthUserTest(){
        step.changeEmailForAuthUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName())
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверка, что можно изменить поле \"name\", если пользователь авторизован")
    @Description("Если пользователь авторизован и поле успешно изменено вернется статус код \"200\"")
    public void changeNameForAuthUserTest(){
        step.changeNameForAuthUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName())
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверка, что нельзя изменить поле \"email\", если пользователь не авторизован")
    @Description("Если пользователь не авторизован и поле не изменено вернется статус код \"401\"")
    public void changeEmailForUnAuthUserTest(){
        step.changeEmailForUnAuthUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName())
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(401);
    }

    @Test
    @DisplayName("Проверка, что нельзя изменить поле \"name\", если пользователь не авторизован")
    @Description("Если пользователь не авторизован и поле не изменено вернется статус код \"401\"")
    public void changeNameForUnAuthUserTest(){
        step.changeNameForUnAuthUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName())
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .statusCode(401);
    }

    @After
    public void tearDown(){
        step.deleteUser(step.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName()))
                .then()
                .statusCode(202);
    }
}
