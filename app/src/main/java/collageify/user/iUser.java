package collageify.user;

import collageify.exception.InvalidNameOptionException;

public interface iUser {
    void addUser(String username, String email, String password, String firstName, String lastname);
    void resetPassword(String username, String email);
    void setUsername(String username);
    void setEmail(String email);
    void setPassword(String password);
    void setName(String first, String last);

    String getUsername();
    String getEmail();
    String getName(int option) throws InvalidNameOptionException;
    


}
