package collageify.user;


import collageify.db.MySQLAccess;
import collageify.exceptions.InvalidOptionException;
public class User implements iUser{
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName; 
    public User(String username, String email, String password, String firstName, String lastname) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastname;
    }
    User(){

    }
    @Override
    public void resetPassword(String username, String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetPassword'");
    }
    @Override
    public void setUsername(String username) {this.username = username;}
    @Override
    public void setEmail(String email) { this.email = email; }
    @Override
    public void setPassword(String password) { this.password =  password; }
    @Override
    public String getUsername() { return this.username; }
    @Override
    public String getEmail() { return this.email; }
    @Override
    public String getName(int option) throws InvalidOptionException {
        try{
            switch (option) {
                case 0:
                    return this.firstName + " " + this.lastName;
                case 1:
                    return this.firstName;
                case 2:
                    return this.lastName;
                default:
                    throw new InvalidOptionException(option);
    
            }
        } catch (Exception e){
            throw e; 
        }
    }
    @Override
    public void setName(String first, String last) {
        this.firstName = first;
        this.lastName = last;
    }
    @Override
    public String getPassword() { return this.password; }

    @Override
    public void UpdateDB() throws Exception{
        MySQLAccess  sql = new MySQLAccess();
            try{
                sql.estConnection();
                sql.addUser(this);
            } catch (Exception e){
                throw e;
            }

    }
}
