import and.point.and.base.uri.GetAndSetBaseUri;
import TestData.TestDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.ApiAuthorizationSteps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

public class ApiChangeDataUserTests {

    ApiAuthorizationSteps step = new ApiAuthorizationSteps();
    TestDataGenerator data = new TestDataGenerator();

    @Before
    public void setUp(){
        String configFilePath = "src/main/resources/config.properties";
        GetAndSetBaseUri setBaseUri = new GetAndSetBaseUri(configFilePath);
        setBaseUri.setUp();
    }

    @Test
    @DisplayName("Проверка, что можно изменить поле \"email\", если пользователь авторизован")
    @Description("Если пользователь авторизован и поле успешно изменено вернется статус код \"200\"")
    public void changeEmailForAuthUserTest(){
        String email = data.getRandomEmail();
        String name = data.getRandomName();
        String password = data.getRandomPassword();
        Response createUser = step.createUser(email, name, password);
        step.changeEmailForAuthUser(email, createUser)
                .then()
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true)).and().body("user.email", is(email));
    }

    @Test
    @DisplayName("Проверка, что можно изменить поле \"name\", если пользователь авторизован")
    @Description("Если пользователь авторизован и поле успешно изменено вернется статус код \"200\"")
    public void changeNameForAuthUserTest(){
        String email = data.getRandomEmail();
        String name = data.getRandomName();
        String password = data.getRandomPassword();
        Response createUser = step.createUser(email, name, password);
        step.changeNameForAuthUser(createUser, name)
                .then()
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true)).and().body("user.name", is(name));
    }

    @Test
    @DisplayName("Проверка, что нельзя изменить поле \"email\", если пользователь не авторизован")
    @Description("Если пользователь не авторизован и поле не изменено вернется статус код \"401\"")
    public void changeEmailForUnAuthUserTest(){
        String email = data.getRandomEmail();
        String name = data.getRandomName();
        String password = data.getRandomPassword();
        step.createUser(email, name, password);
        step.changeEmailForUnAuthUser(data.getRandomEmail())
                .then()
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false)).and().body("message", is("You should be authorised"));
    }

    @Test
    @DisplayName("Проверка, что нельзя изменить поле \"name\", если пользователь не авторизован")
    @Description("Если пользователь не авторизован и поле не изменено вернется статус код \"401\"")
    public void changeNameForUnAuthUserTest(){
        String email = data.getRandomEmail();
        String name = data.getRandomName();
        String password = data.getRandomPassword();
        step.createUser(email, name, password);
        step.changeNameForUnAuthUser(name)
                .then()
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false)).and().body("message", is("You should be authorised"));
    }

    @After
    public void tearDown(){
        step.deleteUser(step.createUser(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName()))
                .then()
                .statusCode(202);
    }
}
