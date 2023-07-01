package collageify.service.collageify.entities;

import collageify.exceptions.NoSPApiException;
import collageify.service.collageify.controller.SpotifyApiController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
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

    public void setAccessToken(Optional<RefreshCredentials> accessToken) { }

    //setters
/*    public void setUuid(UUID uuid) { this.uuid = uuid; }
    public void setId(Integer id) { this.id = id; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public void setUserID(Integer userID) { this.userID = userID; }
    public void setAccessTokenExpDate(Date accessTokenExpDate) { this.accessTokenExpDate = accessTokenExpDate; }
    public void setAccessTokenExpTime(Time accessTokenExpTime) { this.accessTokenExpTime = accessTokenExpTime; }*/
}
