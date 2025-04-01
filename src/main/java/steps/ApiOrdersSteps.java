package steps;

import and.point.and.base.uri.AndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import json.orders.create.order.body.CreateOrderBody;
import json.orders.ingredients.BodyGetIngredients;

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

    @Step("Создание заказа без авторизации")
    public Response createNoAuthOrder(){
        List<String> ingredients = Arrays.asList(getBodyIngredients(0), getBodyIngredients(1));
        CreateOrderBody json = new CreateOrderBody(ingredients);
        return given()
                .when()
                .header("Content-type","application/json")
                .body(json)
                .post(andPoint.getEndPointApiOrders());
    }

    @Step("Создание заказа без ингредиентов")
    public Response createAuthOrderNoIngredients(String accessToken){
        CreateOrderBody json = new CreateOrderBody();
        return given()
                .when()
                .header("Content-type","application/json")
                .header("Authorization", accessToken)
                .body(json)
                .post(andPoint.getEndPointApiOrders());
    }

    @Step("Создание заказа с неверным хешем ингредиентов")
    public Response createAuthOrderInvalidHash(String accessToken){
        List<String> ingredients = Arrays.asList("2w23a112s123", "31qs123sd1321");
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

    @Step("Получение заказа авторизованного пользователя")
    public Response getAuthUserOrder(String accessToken){
        return given()
                .when()
                .header("Authorization", accessToken)
                .get(andPoint.getEndPointApiOrders());
    }

    @Step("Получение заказа без авторизации")
    public Response getNoAuthUserOrder(){
        return given()
                .when()
                .get(andPoint.getEndPointApiOrders());
    }
}
