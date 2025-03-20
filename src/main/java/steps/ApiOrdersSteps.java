package steps;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import json.jsonForApiOrders.ingredients.BodyGetIngredients;

import static io.restassured.RestAssured.given;

public class ApiOrdersSteps {

    //Эндпоинт создания заказа
    private final String API_ORDERS = "/api/orders";

    //Эндпоинт получения данных об ингредиентах
    private final String API_INGREDIENTS = "/api/ingredients";

    Faker data = new Faker();

    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Step("Получение id ингредиента")
    public String getBodyIngredients(int Index){
        BodyGetIngredients bodyIngredients = given()
                .header("Content-type", "application/json")
                .get(API_INGREDIENTS).body().as(BodyGetIngredients.class);
        return bodyIngredients.getData().get(Index).getId();
    }
}
