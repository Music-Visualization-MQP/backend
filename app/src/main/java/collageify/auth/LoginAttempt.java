package collageify.auth;

import collageify.db.MySQLAccess;

public class LoginAttempt {
    private String email;
    private String password;

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
        return password;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }
    public void CheckDB() throws Exception {
            MySQLAccess sql = new MySQLAccess();
            sql.estConnection();
            sql.getAccount(this);
    }
}
