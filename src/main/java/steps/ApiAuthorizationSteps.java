package steps;

import AndPointAndBaseUri.AndPoints;
import TestData.TestDataGenerator;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import json.jsonForApiAuthorization.*;
import json.jsonForApiAuthorization.forBodyResponseGetAccessToken.BodyResponse;

import static io.restassured.RestAssured.given;

public class ApiAuthorizationSteps {

    @Step("Создание уникального пользователя")
    public Response createUser(){
        AndPoints andPoint = new AndPoints();
        TestDataGenerator data = new TestDataGenerator();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(data.getRandomEmail(), data.getRandomPassword(), data.getRandomName());
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationRegister());
    }

    @Step("Создание уже зарегистрированного пользователя")
    public Response createExistingUser(){
        createUser();
        return createUser();
    }

    @Step("Создание пользователя не передав поле \"email\"")
    public Response createUserEmptyEmail(){
        AndPoints andPoint = new AndPoints();
        TestDataGenerator data = new TestDataGenerator();
        ApiAuthorizationUserBodyNoFieldEmail json = new ApiAuthorizationUserBodyNoFieldEmail(data.getRandomPassword(), data.getRandomName());
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationRegister());
    }

    @Step("Создание пользователя не передав поле \"password\"")
    public Response createUserEmptyPassword(){
        AndPoints andPoint = new AndPoints();
        TestDataGenerator data = new TestDataGenerator();
        ApiAuthorizationUserBodyNoFieldPassword json = new ApiAuthorizationUserBodyNoFieldPassword(data.getRandomEmail(), data.getRandomName());
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationRegister());
    }

    @Step("Создание пользователя не передав поле \"name\"")
    public Response createUserEmptyName(){
        AndPoints andPoint = new AndPoints();
        TestDataGenerator data = new TestDataGenerator();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(data.getRandomEmail(), data.getRandomPassword());
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationRegister());
    }

    @Step("Авторизация существующего пользователя")
    public Response authorizationExistingUser(){
        AndPoints andPoint = new AndPoints();
        TestDataGenerator data = new TestDataGenerator();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(data.getRandomEmail(), data.getRandomPassword());
        createUser();
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationLogin());
    }

    @Step("Авторизация с неверным логином")
    public Response authorizationInvalidEmail(){
        AndPoints andPoint = new AndPoints();
        TestDataGenerator data = new TestDataGenerator();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(data.getRandomInvalidEmail(), data.getRandomPassword());
        createUser();
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationLogin());
    }

    @Step("Авторизация с неверным паролем")
    public Response authorizationInvalidPassword(){
        AndPoints andPoint = new AndPoints();
        TestDataGenerator data = new TestDataGenerator();
        ApiAuthorizationUserBody json = new ApiAuthorizationUserBody(data.getRandomEmail(), data.getRandomInvalidPassword());
        createUser();
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post(andPoint.getEndPointAuthorizationLogin());
    }

    @Step("Изменение поля email для авторизованного пользователя")
    public Response changeEmailForAuthUser(){
        AndPoints andPoint = new AndPoints();
        TestDataGenerator data = new TestDataGenerator();
        ChangeForAuthUserFieldEmail json = new ChangeForAuthUserFieldEmail(data.getRandomEmail());
        createUser();
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", getAccessToken())
                .body(json)
                .when()
                .patch(andPoint.getEndPointAuthorizationUser());
    }

    @Step("Изменение поля name для авторизованного пользователя")
    public Response changeNameForAuthUser(){
        AndPoints andPoint = new AndPoints();
        TestDataGenerator data = new TestDataGenerator();
        ChangeForAuthUserFieldName json = new ChangeForAuthUserFieldName(data.getRandomName());
        createUser();
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", getAccessToken())
                .body(json)
                .when()
                .patch(andPoint.getEndPointAuthorizationUser());
    }

    @Step("Изменение поля email для не авторизованного пользователя")
    public Response changeEmailForUnAuthUser(){
        AndPoints andPoint = new AndPoints();
        TestDataGenerator data = new TestDataGenerator();
        ChangeForAuthUserFieldEmail json = new ChangeForAuthUserFieldEmail(data.getRandomEmail());
        createUser();
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .patch(andPoint.getEndPointAuthorizationUser());
    }

    @Step("Изменение поля name для не авторизованного пользователя")
    public Response changeNameForUnAuthUser(){
        AndPoints andPoint = new AndPoints();
        TestDataGenerator data = new TestDataGenerator();
        ChangeForAuthUserFieldName json = new ChangeForAuthUserFieldName(data.getRandomName());
        createUser();
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .patch(andPoint.getEndPointAuthorizationUser());
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
                .delete(andPoint.getEndPointAuthorizationUser());
    }
}
