package collageify.collageify.entities;

import java.sql.Date;
import java.sql.Time;


// TODO: make this throw an exception if anything is called that is not relavant
public class SpotifyRefreshCredentials implements ISpotifyUserCredentials{
    private String accessToken;
    private Date date;
    private Time time;
    private
    SpotifyRefreshCredentials(String accessToken, Date date, Time time){
        this.date = date;
        this.time = time;
        this.accessToken = accessToken;

    }

    @Override
    public Integer getId() {
        return null;


    }

    @Override
    public String getRefreshToken() {
        return null;
    }

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public Integer getUserId() {
        return null;
    }

    @Override
    public Date getAccessTokenExpDate() {
        return this.date;
    }

    @Override
    public Time getAccessTokenExpTime() {
        return this.time;
    }

    @Override
    public Boolean isValid() {
        return System.currentTimeMillis() > this.date.getTime() - 300 * 1000;
    }

}
