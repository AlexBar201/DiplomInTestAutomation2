package AndPointAndBaseUri;

public class AndPoints {
    private final String END_POINT_AUTHORIZATION = "/api/auth";

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
}
