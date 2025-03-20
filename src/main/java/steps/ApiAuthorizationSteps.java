package steps;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import json.jsonForApiAuthorization.ApiAuthorizationUserBody;
import json.jsonForApiAuthorization.ChangeForAuthUserFieldEmail;
import json.jsonForApiAuthorization.ChangeForAuthUserFieldName;
import json.jsonForApiAuthorization.forBodyResponseGetAccessToken.BodyResponse;

import static io.restassured.RestAssured.given;

public class ApiAuthorizationSteps {

    //Создание пользователя
    private final String END_POINT_AUTHORIZATION_REGISTER = "/api/auth/register";

    //Авторизация пользователя
    private final String END_POINT_AUTHORIZATION_LOGIN = "/api/auth/login";

    //Для удаления пользователя ... возможно для чего-то ещё
    private final String END_POINT_AUTHORIZATION_USER = "/api/auth/user";

    Faker data = new Faker();
    String email = data.internet().emailAddress();
    String password = data.internet().password(6,8);
    String name = data.name().username();
    String emailEmpty = "";
    String passwordEmpty = "";
    String nameEmpty = "";
    String invalidEmail = data.internet().emailAddress("invalidMail1488");
    String invalidPassword = data.internet().password(2, 5);

    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Step("Создание уникального пользователя")
    public Response createUser(){
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, password, name);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(END_POINT_AUTHORIZATION_REGISTER);
    }

    @Step("Создание уже зарегистрированного пользователя")
    public Response createExistingUser(){
        createUser();
        return createUser();
    }

    @Step("Создание пользователя не передав поле \"email\"")
    public Response createUserEmptyEmail(){
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(emailEmpty, password, name);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(END_POINT_AUTHORIZATION_REGISTER);
    }

    @Step("Создание пользователя не передав поле \"password\"")
    public Response createUserEmptyPassword(){
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, passwordEmpty, name);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(END_POINT_AUTHORIZATION_REGISTER);
    }

    @Step("Создание пользователя не передав поле \"name\"")
    public Response createUserEmptyName(){
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, password, nameEmpty);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(END_POINT_AUTHORIZATION_REGISTER);
    }

    @Step("Авторизация существующего пользователя")
    public Response authorizationExistingUser(){
        createUser();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, password);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(END_POINT_AUTHORIZATION_LOGIN);
    }

    @Step("Авторизация с неверным логином")
    public Response authorizationInvalidEmail(){
        createUser();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(invalidEmail, password);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(END_POINT_AUTHORIZATION_LOGIN);
    }

    @Step("Авторизация с неверным паролем")
    public Response authorizationInvalidPassword(){
        createUser();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, invalidPassword);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(END_POINT_AUTHORIZATION_LOGIN);
    }

    @Step("Изменение поля email для авторизованного пользователя")
    public Response changeEmailForAuthUser(){
        createUser();
        ChangeForAuthUserFieldEmail json = new ChangeForAuthUserFieldEmail(email);
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", getAccessToken())
                .body(json)
                .when()
                .patch(END_POINT_AUTHORIZATION_USER);
    }

    @Step("Изменение поля name для авторизованного пользователя")
    public Response changeNameForAuthUser(){
        createUser();
        ChangeForAuthUserFieldName json = new ChangeForAuthUserFieldName(name);
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", getAccessToken())
                .body(json)
                .when()
                .patch(END_POINT_AUTHORIZATION_USER);
    }

    @Step("Изменение поля email для не авторизованного пользователя")
    public Response changeEmailForUnAuthUser(){
        createUser();
        ChangeForAuthUserFieldEmail json = new ChangeForAuthUserFieldEmail(email);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .patch(END_POINT_AUTHORIZATION_USER);
    }

    @Step("Изменение поля name для не авторизованного пользователя")
    public Response changeNameForUnAuthUser(){
        createUser();
        ChangeForAuthUserFieldName json = new ChangeForAuthUserFieldName(name);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .patch(END_POINT_AUTHORIZATION_USER);
    }

    @Step("Получение accessToken")
    public String getAccessToken(){
        BodyResponse bodyResponse = createUser().body().as(BodyResponse.class);
        return bodyResponse.getAccessToken();
    }

    @Step("Удаление пользователя")
    public Response deleteUser(){
        return given()
                .header("Authorization", getAccessToken())
                .when()
                .delete(END_POINT_AUTHORIZATION_USER);
    }
}
