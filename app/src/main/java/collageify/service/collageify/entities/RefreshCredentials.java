package collageify.service.collageify.entities;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

public class RefreshCredentials {
    Optional<String> accessToken;
    Optional<Date> accessTokenExpDate;
    Optional<Time> accessTokenExpTime;

    public RefreshCredentials(String accessToken, Date date, Time time){
        if(accessToken.isPresent() && date.isPresent() && time.isPresent()){

            this.accessToken = accessToken;
            this.accessTokenExpDate = date;
            this.accessTokenExpTime = time;
        }
    }
    Optional<String> getAccessToken(){ return this.accessToken; }
    Optional<Date> getExpDate(){ return this.accessTokenExpDate;}
    Optional<Time> getExpTime(){return this.accessTokenExpTime;}



}
