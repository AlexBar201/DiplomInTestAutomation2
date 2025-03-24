import and.point.and.base.uri.GetAndSetBaseUri;
import TestData.TestDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.ApiAuthorizationSteps;

import static org.hamcrest.Matchers.equalTo;

public class ApiAuthorizationUserTests {

    ApiAuthorizationSteps step = new ApiAuthorizationSteps();
    TestDataGenerator data = new TestDataGenerator();

    @Before
    public void setUp(){
        String configFilePath = "src/main/resources/config.properties";
        GetAndSetBaseUri setBaseUri = new GetAndSetBaseUri(configFilePath);
        setBaseUri.setUp();
    }

    @Test
    @DisplayName("Проверка, что можно авторизоваться, под существующим логином и правильным паролем")
    @Description("Если передать корректные поля вернется статус код \"200\"")
    public void authorizationExistingUserTest(){
        String email = data.getRandomEmail();
        String password = data.getRandomPassword();
        String name = data.getRandomName();
        step.createUser(email, password, name);
        step.authorizationExistingUser(email, password)
                .then()
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка, что нельзя авторизоваться, под не существующим логином и правильным паролем")
    @Description("Если передать некорректный логин вернется статус код \"401 Unauthorized\"")
    public void authorizationInvalidEmailUserTest(){
        String email = data.getRandomEmail();
        String password = data.getRandomPassword();
        String name = data.getRandomName();
        String invalidEmail = data.getRandomInvalidEmail();
        step.createUser(email, password, name);
        step.authorizationInvalidEmail(password, invalidEmail)
                .then()
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Проверка, что нельзя авторизоваться, под существующим логином и не правильным паролем")
    @Description("Если передать некорректный пароль вернется статус код \"401 Unauthorized\"")
    public void authorizationInvalidPasswordUserTest(){
        step.authorizationInvalidPassword(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName(), data.getRandomInvalidPassword())
                .then()
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false));
    }

    @After
    public void tearDown(){
        step.deleteUser(step.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName()))
                .then()
                .statusCode(202);
    }
}
