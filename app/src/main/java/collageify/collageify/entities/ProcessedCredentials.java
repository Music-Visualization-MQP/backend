package collageify.collageify.entities;

import collageify.web.exceptions.NoSPApiException;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;
import java.util.UUID;

public class ProcessedCredentials {
    private UUID uuid;
    private Integer id;
    private String refreshToken;
    private String accessToken;
    private Integer userID;
    private Date accessTokenExpDate;
    private Time accessTokenExpTime;

    public ProcessedCredentials(UUID uuid,Integer id, String refreshToken, String accessToken, Integer userID, Date accessTokenExpDate, Time accessTokenExpTime){
        this.uuid = uuid;
        this.id = id;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.userID = userID;
        this.accessTokenExpDate = accessTokenExpDate;
        this.accessTokenExpTime = accessTokenExpTime;
    }

    //getters
    public UUID getUuid() { return uuid; }
    Integer getId() { return id; }
    public String getRefreshToken() { return refreshToken; }
    public String getAccessToken() { return accessToken; }
    Integer getUserID() { return userID; }
    public Date getAccessTokenExpDate() { return accessTokenExpDate; }
    public Time getAccessTokenExpTime() { return accessTokenExpTime; }

    public void setAccessToken(Optional<RefreshCredentials> accessToken) throws NoSPApiException {
        if(accessToken.isPresent()){
            RefreshCredentials refresh = accessToken.get();
            this.accessToken = refresh.accessToken;
            this.accessTokenExpDate = refresh.accessTokenExpDate;
            this.accessTokenExpTime = refresh.accessTokenExpTime;

        }else throw new NoSPApiException("set access token fault");
    }

    public Boolean isValid(){
        long millis = System.currentTimeMillis();
        return millis < this.accessTokenExpDate.getTime();
    }

    //setters
/*    public void setUuid(UUID uuid) { this.uuid = uuid; }
    public void setId(Integer id) { this.id = id; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public void setUserID(Integer userID) { this.userID = userID; }
    public void setAccessTokenExpDate(Date accessTokenExpDate) { this.accessTokenExpDate = accessTokenExpDate; }
    public void setAccessTokenExpTime(Time accessTokenExpTime) { this.accessTokenExpTime = accessTokenExpTime; }*/
}
