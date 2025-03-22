package steps;

import AndPointAndBaseUri.AndPoints;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import json.jsonForApiOrders.createOrderBody.CreateOrderBody;
import json.jsonForApiOrders.ingredients.BodyGetIngredients;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiOrdersSteps {
    AndPoints andPoint = new AndPoints();
    ApiAuthorizationSteps step = new ApiAuthorizationSteps();

    @Step("Создание заказа с авторизацией")
    public Response createAuthOrder(String email, String password, String name){
        Response createUser = step.createUser(email, password, name);
        String authToken = step.getAccessToken(createUser);
        CreateOrderBody json = new CreateOrderBody();
        List<String> ingredients = Arrays.asList(getBodyIngredients(0), getBodyIngredients(1));
        json.setIngredients(ingredients);
        Response response = given()
                .when()
                .header("Content-type","application/json")
                .header("Authorization", authToken)
                .body(json)
                .post(andPoint.getEndPointApiOrders());
        return response;
    }

    @Step("Получение id ингредиента")
    public String getBodyIngredients(int Index){
        BodyGetIngredients bodyIngredients = given()
                .get(andPoint.getEndPointApiIngredients()).body().as(BodyGetIngredients.class);
        return bodyIngredients.getData().get(Index).getId();
    }
}
