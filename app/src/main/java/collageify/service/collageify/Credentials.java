package collageify.service.collageify;

import java.sql.Date;
import java.sql.Time;

public class Credentials {
    Integer id;
    String refreshToken;
    String accessToken;
    Integer userID;
    Date accessTokenExpDate;
    Time accessTokenExpTime;

    public Credentials(Integer id, String refreshToken, String accessToken, Integer userID, Date accessTokenExpDate, Time accessTokenExpTime){
        this.id = id;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.userID = userID;
        this.accessTokenExpDate = accessTokenExpDate;
        this.accessTokenExpTime = accessTokenExpTime;
    }

}
