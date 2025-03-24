package and.point.and.base.uri;

public class AndPoints {
    private final String END_POINT_AUTHORIZATION = "/api/auth";
    private final String END_POINT_API_ORDERS = "/api/orders";

    //Ручка для создания пользователя
    public String getEndPointAuthorizationRegister() {
        return END_POINT_AUTHORIZATION + "/register";
    }

    //Ручка для авторизации пользователя
    public String getEndPointAuthorizationLogin() {
        return END_POINT_AUTHORIZATION + "/login";
    }

    //Ручка для удаления пользователя и возможно для чего-то ещё
    public String getEndPointAuthorizationUser() {
        return END_POINT_AUTHORIZATION + "/user";
    }

    //Ручка для получения данных об ингредиентах
    public String getEndPointApiIngredients(){
        return "/api/ingredients";
    }

    //Ручка для создания заказа
    public String getEndPointApiOrders(){
        return END_POINT_API_ORDERS;
    }

    //Ручка для получения всех заказов
    public String getEndPointApiOrdersAll(){
        return END_POINT_API_ORDERS + "/all";
    }
}
