package json.authorization;

public class ChangeForAuthUserFieldEmail {
    private String email;

    public ChangeForAuthUserFieldEmail(String email){
        this.email = email;
    }

    public ChangeForAuthUserFieldEmail(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
