package collageify.db;

import java.sql.SQLException;
import java.time.LocalDateTime;

public interface IDBAccess {
    public void estConnection() throws SQLException;
    public void addPlayed(Integer userID, String username, String spURI, String artistName, String albumName, String trackName, Integer popularity, Integer durationMS) throws Exception; 
    public void addSpotifyCredentials(Integer userID, String accessToken, String refreshToken, LocalDateTime accessTokenExp) throws SQLException;
    public void getTrackPlayedUser();
    public void getTrackPlayedPublic();
    public void getAuthCredentials(Integer userID) throws SQLException;
}
