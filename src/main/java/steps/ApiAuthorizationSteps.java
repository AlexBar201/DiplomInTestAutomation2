package steps;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import json.CreateUserBody;

import static io.restassured.RestAssured.given;

public class ApiAuthorizationSteps {

    private final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    //Создание пользователя
    private final String END_POINT_AUTHORIZATION_REGISTER = "/api/auth/register";

    //Авторизация пользователя
    private final String END_POINT_AUTHORIZATION_LOGIN = "/api/auth/login";

    //Для удаления пользователя ... возможно для чего-то ещё
    private final String END_POINT_AUTHORIZATION_USER = "/api/auth/user";

    Faker data = new Faker();
    String email = data.internet().emailAddress();
    String password = data.internet().password(4,8);
    String name = data.name().username();
    String emailEmpty = "";
    String passwordEmpty = "";
    String nameEmpty = "";

    public void setUp(){
        RestAssured.baseURI = BASE_URI;
    }

    @Step("Создание уникального пользователя")
    public Response createUser(){
        CreateUserBody json = new CreateUserBody(email, password, name);
        Response response = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(END_POINT_AUTHORIZATION_REGISTER);
        return response;
    }

    @Step("Создание уже зарегистрированного пользователя")
    public Response createExistingUser(){
        createUser();
        Response response = createUser();
        return response;
    }

    @Step("Создание пользователя не передав поле \"email\"")
    public Response createUserEmptyEmail(){
        CreateUserBody json = new CreateUserBody(emailEmpty, password, name);
        Response response = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(END_POINT_AUTHORIZATION_REGISTER);
        return response;
    }

    @Step("Создание пользователя не передав поле \"password\"")
    public Response createUserEmptyPassword(){
        CreateUserBody json = new CreateUserBody(email, passwordEmpty, name);
        Response response = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(END_POINT_AUTHORIZATION_REGISTER);
        return response;
    }

    @Step("Создание пользователя не передав поле \"name\"")
    public Response createUserEmptyName(){
        CreateUserBody json = new CreateUserBody(email, password, nameEmpty);
        Response response = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(END_POINT_AUTHORIZATION_REGISTER);
        return response;
    }

    @Step("Удаление пользователя")
    public Response deleteUser(){
        String accessToken = "";
        Response response = given()
                .header("Authorization", accessToken)
                .when()
                .delete(END_POINT_AUTHORIZATION_USER);
        return response;
    }
}
