package collageify.user;

import collageify.exceptions.InvalidOptionException;

public interface iUser {
    void resetPassword(String username, String email);
    void setUsername(String username);
    void setEmail(String email);
    void setPassword(String password);
    void setName(String first, String last);

    String getUsername();
    String getEmail();
    String getPassword();
    String getName(int option) throws InvalidOptionException;

    void UpdateDB() throws Exception;
    


}
