package collageify.deprecated.auth;

import collageify.db.MySQLAccess;

public class LoginAttempt {
    private String email;
    private String password;

    // Empty constructor required for deserialization
    public LoginAttempt(String email, String password) {
        this.email = email;
        this.password = password;
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
    public boolean CheckDB() throws Exception {
            MySQLAccess sql = new MySQLAccess();
            sql.estConnection();
            return sql.getAccount(this);
    }
}
