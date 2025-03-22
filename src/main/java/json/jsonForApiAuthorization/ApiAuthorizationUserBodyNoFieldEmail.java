package json.jsonForApiAuthorization;

public class ApiAuthorizationUserBodyNoFieldEmail {
    private String password;
    private String name;

    public ApiAuthorizationUserBodyNoFieldEmail(String password, String name){
        this.password = password;
        this.name = name;
    }

    public ApiAuthorizationUserBodyNoFieldEmail(){}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
