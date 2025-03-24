import and.point.and.base.uri.GetAndSetBaseUri;
import TestData.TestDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.ApiAuthorizationSteps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

public class ApiCreateUserTests {

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
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка, что нельзя создать уже существующего пользователя")
    @Description("При попытке создать уже существующего пользователя возвращается статус код \"403 Forbidden\"")
    public void createExistingUserTest(){
        String email = data.getRandomEmail();
        String password = data.getRandomPassword();
        String name = data.getRandomName();
        step.createUser(email, password, name);
        step.createUser(email, password, name)
                .then()
                .statusCode(403)
                .assertThat()
                .body("success", equalTo(false)).and().body("message", is("User already exists"));
    }

    @Test
    @DisplayName("Проверка, что нельзя создать пользователя не передав обязательное поле \"email\"")
    @Description("Если не передать одно из обязательных полей возвращается ошибка \"403 Forbidden\"")
    public void createUniqueUserNoFieldEmailTest(){
        step.createUserEmptyEmail(data.getRandomPassword(), data.getRandomName())
                .then()
                .statusCode(403)
                .assertThat()
                .body("success", equalTo(false)).and().body("message", is("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Проверка, что нельзя создать пользователя не передав обязательное поле \"password\"")
    @Description("Если не передать одно из обязательных полей возвращается ошибка \"403 Forbidden\"")
    public void createUniqueUserNoFieldPasswordTest(){
        step.createUserEmptyPassword(data.getRandomEmail(), data.getRandomName())
                .then()
                .statusCode(403)
                .assertThat()
                .body("success", equalTo(false)).and().body("message", is("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Проверка, что нельзя создать пользователя не передав обязательное поле \"name\"")
    @Description("Если не передать одно из обязательных полей возвращается ошибка \"403 Forbidden\"")
    public void createUniqueUserNoFieldNameTest(){
        step.createUserEmptyName(data.getRandomEmail(), data.getRandomPassword())
                .then()
                .statusCode(403)
                .assertThat()
                .body("success", equalTo(false)).and().body("message", is("Email, password and name are required fields"));
    }

    @After
    public void tearDown(){
        step.deleteUser(step.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName()))
                .then()
                .statusCode(202);
    }
}
