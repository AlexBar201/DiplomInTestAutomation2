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

    @Step("Создание заказа с авторизацией")
    public Response createAuthOrder(String accessToken){
        List<String> ingredients = Arrays.asList(getBodyIngredients(0), getBodyIngredients(1));
        CreateOrderBody json = new CreateOrderBody(ingredients);
        return given()
                .when()
                .header("Content-type","application/json")
                .header("Authorization", accessToken)
                .body(json)
                .post(andPoint.getEndPointApiOrders());
    }

    @Step("Получение id ингредиента")
    public String getBodyIngredients(int Index){
        BodyGetIngredients bodyIngredients = given()
                .get(andPoint.getEndPointApiIngredients()).body().as(BodyGetIngredients.class);
        return bodyIngredients.getData().get(Index).getId();
    }
}
