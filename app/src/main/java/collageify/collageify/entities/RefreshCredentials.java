package collageify.collageify.entities;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

public class RefreshCredentials {
    String accessToken;
    Date accessTokenExpDate;
    Time accessTokenExpTime;

    public RefreshCredentials(String accessToken, Date date, Time time){

        this.accessToken = accessToken;
        this.accessTokenExpDate = date;
        this.accessTokenExpTime = time;

    }
    Optional<String> getAccessToken(){ return Optional.of(this.accessToken); }
    Optional<Date> getExpDate(){ return Optional.of(this.accessTokenExpDate);}
    Optional<Time> getExpTime(){return Optional.of(this.accessTokenExpTime);}



}
