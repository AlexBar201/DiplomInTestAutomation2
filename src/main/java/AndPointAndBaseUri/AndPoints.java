package AndPointAndBaseUri;

public class AndPoints {
    private final String END_POINT_AUTHORIZATION = "/api/auth";

    //Ручка для создания пользователя
    public String getEND_POINT_AUTHORIZATION_REGISTER() {
        return END_POINT_AUTHORIZATION + "/register";
    }

    //Ручка для авторизации пользователя
    public String getEND_POINT_AUTHORIZATION_LOGIN() {
        return END_POINT_AUTHORIZATION + "/login";
    }

    //Ручка для удаления пользователя и возможно для чего-то ещё
    public String getEND_POINT_AUTHORIZATION_USER() {
        return END_POINT_AUTHORIZATION + "/user";
    }
}
