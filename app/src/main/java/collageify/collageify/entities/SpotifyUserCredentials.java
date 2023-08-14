package collageify.collageify.entities;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;


public class SpotifyUserCredentials {
    final private Integer id;
    final private String refreshToken;
    final private String accessToken;
    final private Integer userID;
    final private Date accessTokenExpDate;
    final private Time accessTokenExpTime;

    /**
     *
     * @param id integer representing id in the table of spotify credentials
     * @param refreshToken string representing token used for fetching new access token when needed
     * @param accessToken string which is always invalid when pulled from the database since it is never updated
     * @param userID integer representing the user's id within the database
     * @param accessTokenExpDate sql date object representing the date at which the token expires
     * @param accessTokenExpTime sql time object representing the time at which the token will expire
     */
    public SpotifyUserCredentials(Integer id, String refreshToken, String accessToken, Integer userID, Date accessTokenExpDate, Time accessTokenExpTime){
        this.id = id;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.userID = userID;
        this.accessTokenExpDate = accessTokenExpDate;
        this.accessTokenExpTime = accessTokenExpTime;
    }

    //getters
    public Integer getId() { return id; }
    public String getRefreshToken() { return refreshToken; }
    public String getAccessToken() { return accessToken; }
    public Integer getUserId() { return userID; }
    public Date getAccessTokenExpDate() { return accessTokenExpDate; }
    public Time getAccessTokenExpTime() { return accessTokenExpTime; }

    /*public void setAccessToken(Optional<RefreshCredentials> accessToken) throws NoSPApiException {
        if(accessToken.isPresent()){
            RefreshCredentials refresh = accessToken.get();
            this.accessToken = refresh.accessToken;
            this.accessTokenExpDate = refresh.accessTokenExpDate;
            this.accessTokenExpTime = refresh.accessTokenExpTime;

        }else throw new NoSPApiException("set access token fault");
    }*/

    public Boolean isValid() {
        long millis = System.currentTimeMillis();
        //do we really want this 5 minute delay or whatever I understand it purpose, but it may prove un nescicary
        return millis > this.accessTokenExpDate.getTime() - 300 * 1000;
        //return millis < this.accessTokenExpDate.getTime(); this should be somewhere else
    }



    //setters
/*    public void setUuid(UUID uuid) { this.uuid = uuid; }
    public void setId(Integer id) { this.id = id; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public void setUserID(Integer userID) { this.userID = userID; }
    public void setAccessTokenExpDate(Date accessTokenExpDate) { this.accessTokenExpDate = accessTokenExpDate; }
    public void setAccessTokenExpTime(Time accessTokenExpTime) { this.accessTokenExpTime = accessTokenExpTime; }*/
}
