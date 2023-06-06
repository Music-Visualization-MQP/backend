package collageify.db;
public interface IDBAccess {
    public void estConnection() throws Exception;
    public void addPlayed(Integer userID, String username, String spURI, String artistName, String albumName, String trackName, Integer popularity, Integer durationMS) throws Exception; 
    public void getTrackPlayedUser();
    public void getTrackPlayedPublic();
}
