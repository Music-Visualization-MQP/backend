package collageify.service.collageify.spotify;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class RefreshCredentials {
    Integer id;
    String accessToken;
    Integer userID;
    Date accessTokenExpDate;
    Time accessTokenExpTime;

    public RefreshCredentials(Integer id, String refreshToken, String accessToken, Integer userID, Date accessTokenExpDate, Time accessTokenExpTime){
        this.id = id;
        this.accessToken = accessToken;
        this.userID = userID;
        this.accessTokenExpDate = accessTokenExpDate;
        this.accessTokenExpTime = accessTokenExpTime;
    }

}
