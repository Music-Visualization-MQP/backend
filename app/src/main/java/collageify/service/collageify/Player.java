package collageify.service.collageify;
import collageify.db.SQLAccess;
import collageify.exceptions.NoSPApiException;
import com.mysql.cj.PreparedQuery;

import java.sql.SQLException;
import java.util.Optional;

public class Player {

    private Integer userID;
    private String username;

    private Integer progressMS;
    private Double progressPercent;

    private String spURI;
    private String artistName;
    private String albumName;
    private String trackName;
    private Integer popularity;
    private Integer durationMS;

    private Boolean enoughPlayed;


    public SPAccess spAccess;

    public Player(Integer userID) throws NoSPApiException, SQLException {
        this.spAccess = new SPAccess(userID);
    }

    public Player(Integer userID, String username, Integer progressMS, String spURI, String artistName, String albumName, String trackName, Integer popularity, Integer durationMS) throws NoSPApiException {
        this.userID = userID;
        this.username = username;
        this.progressMS = progressMS;
        this.spURI = spURI;
        this.artistName = artistName;
        this.albumName = albumName;
        this.trackName = trackName;
        this.popularity = popularity;
        this.durationMS = durationMS;
        this.progressPercent = (double) (progressMS / durationMS);
        if(this.progressPercent >= 0.66d){
            this.enoughPlayed = true;
        } else{
            this.enoughPlayed = false;
        }
    }

    public void UpdateProgress(Integer newProgressMS){
        this.progressMS = newProgressMS;
        this.progressPercent = (double) (progressMS / durationMS);
        if(this.progressPercent >= 0.66d){
            this.enoughPlayed = true;
        } else{
            this.enoughPlayed = false;
        }
    }

    public void UpdateDB() throws Exception{
        if(enoughPlayed == true){
            SQLAccess  sql = new SQLAccess();
            try{
                sql.estConnection();
                sql.addPlayed(userID, username, spURI, artistName, albumName, trackName, popularity, durationMS);
            } catch (Exception e){
                throw e;
            }
        }
    }

    public void run() throws Exception, NoSPApiException{
        while (this.spAccess.credentials.get().isTokenValid()){

            System.out.println("waiting");
            if(Optional.ofNullable(this.spAccess.requestData()).isPresent()){
                System.out.println(this.spAccess.requestData());
                wait(2500);
            } else {
                System.out.println("i got nothing");
            }
        }

    }




    /* void setUserID(Integer userID){
        this.userID = userID;
    }
    void setUserName(String username){
        this.username = username;
    }
    void setUserID(Integer userID){
        this.userID = userID;
    }
    void setUserID(Integer userID){
        this.userID = userID;
    }
    void setUserName; */


}