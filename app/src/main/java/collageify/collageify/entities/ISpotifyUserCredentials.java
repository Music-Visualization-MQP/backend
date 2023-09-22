package collageify.collageify.entities;

import java.sql.Date;
import java.sql.Time;

public interface ISpotifyUserCredentials {
    public Integer getId();
    public String getRefreshToken();
    public String getAccessToken();
    public Integer getUserId();
    public Date getAccessTokenExpDate();
    public Time getAccessTokenExpTime();
    public Boolean isValid();

}
