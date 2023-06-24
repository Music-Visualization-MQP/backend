package collageify.db;

import collageify.exceptions.NoSPApiException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public interface IDBAccess {
    public void estConnection() throws SQLException;
    public void addPlayed(Integer userID, String username, String spURI, String artistName, String albumName, String trackName, Integer popularity, Integer durationMS, String json) throws Exception;
    public void addSpotifyCredentials(Integer userID, String accessToken, String refreshToken, LocalDateTime accessTokenExp) throws SQLException;
    public void getTrackPlayedUser();
    public void getTrackPlayedPublic();
    public Optional<ResultSet> getAuthCredentials() throws SQLException, NoSPApiException;
}
