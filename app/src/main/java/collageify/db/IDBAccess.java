package collageify.db;
import collageify.deprecated.auth.LoginAttempt;
import collageify.entity.User;
public interface IDBAccess {
    public void estConnection() throws Exception;
    public void addUser(User user) throws Exception;
    public boolean getAccount(LoginAttempt loginAttempt) throws Exception;
    public void addPlayed(Integer userID, String username, String spURI, String artistName, String albumName, String trackName, Integer popularity, Integer durationMS) throws Exception; 
    public void getTrackPlayedUser();
    public void getTrackPlayedPublic();
}
