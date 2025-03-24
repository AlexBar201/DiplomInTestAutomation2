package steps;

import and.point.and.base.uri.AndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import json.jsonForApiAuthorization.ApiAuthorizationUserBody;
import json.jsonForApiAuthorization.ApiAuthorizationUserBodyNoFieldPassword;
import json.jsonForApiAuthorization.ChangeForAuthUserFieldEmail;
import json.jsonForApiAuthorization.ChangeForAuthUserFieldName;
import json.jsonForApiAuthorization.forBodyResponseGetAccessToken.BodyResponse;

import static io.restassured.RestAssured.given;

public class ApiAuthorizationSteps {

    AndPoints andPoint = new AndPoints();

    @Step("Создание уникального пользователя")
    public Response createUser(String email, String password, String name){
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, password, name);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationRegister());
    }

    @Step("Создание пользователя не передав поле \"email\"")
    public Response createUserEmptyEmail(String password, String name){
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(null, password, name);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationRegister());
    }

    @Step("Создание пользователя не передав поле \"password\"")
    public Response createUserEmptyPassword(String email, String name){
        ApiAuthorizationUserBodyNoFieldPassword json = new ApiAuthorizationUserBodyNoFieldPassword(email, name);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationRegister());
    }

    @Step("Создание пользователя не передав поле \"name\"")
    public Response createUserEmptyName(String email, String password){
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, password);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationRegister());
    }

    @Step("Авторизация существующего пользователя")
    public Response authorizationExistingUser(String email, String password){
        ApiAuthorizationUserBody json1 = new ApiAuthorizationUserBody(email, password);
        return given()
                .header("Content-type", "application/json")
                .body(json1)
                .when()
                .post(andPoint.getEndPointAuthorizationLogin());
    }

    @Step("Авторизация с неверным логином")
    public Response authorizationInvalidEmail(String password, String invalidEmail){
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(invalidEmail, password);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationLogin());
    }

    @Step("Авторизация с неверным паролем")
    public Response authorizationInvalidPassword(String email, String password, String name, String invalidPassword){
        createUser(email, password, name);
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(email, invalidPassword);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationLogin());
    }

    @Step("Изменение поля email для авторизованного пользователя")
    public Response changeEmailForAuthUser(String email, Response createUser){
        ChangeForAuthUserFieldEmail json = new ChangeForAuthUserFieldEmail(email);
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", getAccessToken(createUser))
                .body(json)
                .when()
                .patch(andPoint.getEndPointAuthorizationUser());
    }

    @Step("Изменение поля name для авторизованного пользователя")
    public Response changeNameForAuthUser(Response createUser, String name){
        ChangeForAuthUserFieldName json = new ChangeForAuthUserFieldName(name);
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", getAccessToken(createUser))
                .body(json)
                .when()
                .patch(andPoint.getEndPointAuthorizationUser());
    }

    @Step("Изменение поля email для не авторизованного пользователя")
    public Response changeEmailForUnAuthUser(String email){
        ChangeForAuthUserFieldEmail json = new ChangeForAuthUserFieldEmail(email);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .patch(andPoint.getEndPointAuthorizationUser());
    }

    @Step("Изменение поля name для не авторизованного пользователя")
    public Response changeNameForUnAuthUser(String name){
        ChangeForAuthUserFieldName json = new ChangeForAuthUserFieldName(name);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .patch(andPoint.getEndPointAuthorizationUser());
    }

    @Step("Получение accessToken")
    public String getAccessToken(Response createUser){
        BodyResponse bodyResponse = createUser.body().as(BodyResponse.class);
        return bodyResponse.getAccessToken();
    }

    @Step("Удаление пользователя")
    public Response deleteUser(Response createUser){
        return given()
                .header("Authorization", getAccessToken(createUser))
                .when()
                .delete(andPoint.getEndPointAuthorizationUser());
    }
}
