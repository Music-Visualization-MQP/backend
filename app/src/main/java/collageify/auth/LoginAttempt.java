package collageify.auth;

public class LoginAttempt {
    private String email;
    private String pw;

    // Empty constructor required for deserialization
    public LoginAttempt() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
