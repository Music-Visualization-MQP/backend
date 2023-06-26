package collageify.service.collageify.entities;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Optional;

public class RefreshCredentials {
    String accessToken;
    Date accessTokenExpDate;
    Time accessTokenExpTime;

    public RefreshCredentials(Optional<String> accessToken, Optional<Date> date, Optional<Time> time){
        if(accessToken.isPresent() && date.isPresent() && time.isPresent()){

        }
        this.id = id;
        this.accessToken = accessToken;
        this.userID = userID;
        this.accessTokenExpDate = accessTokenExpDate;
        this.accessTokenExpTime = accessTokenExpTime;
    }

}
