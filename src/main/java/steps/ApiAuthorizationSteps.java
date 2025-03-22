package steps;

import AndPointAndBaseUri.AndPoints;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import json.jsonForApiAuthorization.ApiAuthorizationUserBody;
import json.jsonForApiAuthorization.ChangeForAuthUserFieldEmail;
import json.jsonForApiAuthorization.ChangeForAuthUserFieldName;
import json.jsonForApiAuthorization.forBodyResponseGetAccessToken.BodyResponse;

import static io.restassured.RestAssured.given;

public class ApiAuthorizationSteps {

    Faker data = new Faker();
    String email = data.internet().emailAddress();
    String password = data.internet().password(6,8);
    String name = data.name().username();
    String emailEmpty = "";
    String passwordEmpty = "";
    String nameEmpty = "";
    String invalidEmail = data.internet().emailAddress("invalidMail1488");
    String invalidPassword = data.internet().password(2, 5);

    @Step("Создание уникального пользователя")
    public Response createUser(){
        AndPoints andPoint = new AndPoints();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, password, name);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEND_POINT_AUTHORIZATION_REGISTER());
    }

    @Step("Создание уже зарегистрированного пользователя")
    public Response createExistingUser(){
        createUser();
        return createUser();
    }

    @Step("Создание пользователя не передав поле \"email\"")
    public Response createUserEmptyEmail(){
        AndPoints andPoint = new AndPoints();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(emailEmpty, password, name);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEND_POINT_AUTHORIZATION_REGISTER());
    }

    @Step("Создание пользователя не передав поле \"password\"")
    public Response createUserEmptyPassword(){
        AndPoints andPoint = new AndPoints();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, passwordEmpty, name);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEND_POINT_AUTHORIZATION_REGISTER());
    }

    @Step("Создание пользователя не передав поле \"name\"")
    public Response createUserEmptyName(){
        AndPoints andPoint = new AndPoints();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, password, nameEmpty);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEND_POINT_AUTHORIZATION_REGISTER());
    }

    @Step("Авторизация существующего пользователя")
    public Response authorizationExistingUser(){
        AndPoints andPoint = new AndPoints();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, password);
        createUser();
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEND_POINT_AUTHORIZATION_LOGIN());
    }

    @Step("Авторизация с неверным логином")
    public Response authorizationInvalidEmail(){
        AndPoints andPoint = new AndPoints();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(invalidEmail, password);
        createUser();
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEND_POINT_AUTHORIZATION_LOGIN());
    }

    @Step("Авторизация с неверным паролем")
    public Response authorizationInvalidPassword(){
        AndPoints andPoint = new AndPoints();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, invalidPassword);
        createUser();
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEND_POINT_AUTHORIZATION_LOGIN());
    }

    @Step("Изменение поля email для авторизованного пользователя")
    public Response changeEmailForAuthUser(){
        AndPoints andPoint = new AndPoints();
        ChangeForAuthUserFieldEmail json = new ChangeForAuthUserFieldEmail(email);
        createUser();
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", getAccessToken())
                .body(json)
                .when()
                .patch(andPoint.getEND_POINT_AUTHORIZATION_USER());
    }

    @Step("Изменение поля name для авторизованного пользователя")
    public Response changeNameForAuthUser(){
        AndPoints andPoint = new AndPoints();
        ChangeForAuthUserFieldName json = new ChangeForAuthUserFieldName(name);
        createUser();
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", getAccessToken())
                .body(json)
                .when()
                .patch(andPoint.getEND_POINT_AUTHORIZATION_USER());
    }

    @Step("Изменение поля email для не авторизованного пользователя")
    public Response changeEmailForUnAuthUser(){
        AndPoints andPoint = new AndPoints();
        ChangeForAuthUserFieldEmail json = new ChangeForAuthUserFieldEmail(email);
        createUser();
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .patch(andPoint.getEND_POINT_AUTHORIZATION_USER());
    }

    @Step("Изменение поля name для не авторизованного пользователя")
    public Response changeNameForUnAuthUser(){
        AndPoints andPoint = new AndPoints();
        ChangeForAuthUserFieldName json = new ChangeForAuthUserFieldName(name);
        createUser();
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .patch(andPoint.getEND_POINT_AUTHORIZATION_USER());
    }

    @Step("Получение accessToken")
    public String getAccessToken(){
        BodyResponse bodyResponse = createUser().body().as(BodyResponse.class);
        return bodyResponse.getAccessToken();
    }

    @Step("Удаление пользователя")
    public Response deleteUser(){
        AndPoints andPoint = new AndPoints();
        return given()
                .header("Authorization", getAccessToken())
                .when()
                .delete(andPoint.getEND_POINT_AUTHORIZATION_USER());
    }
}
