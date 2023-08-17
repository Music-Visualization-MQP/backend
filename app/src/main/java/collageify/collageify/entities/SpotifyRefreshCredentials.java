package collageify.collageify.entities;

import java.sql.Date;
import java.sql.Time;

public abstract class SpotifyRefreshCredentials implements ISpotifyUserCredentials{
    String accessToken;
    Date accessTokenExpDate;
    Time accessTokenExpTime;
    Integer id;

    /**
     *
     * @param id
     * @param accessToken
     */
    public SpotifyRefreshCredentials(Integer id, String accessToken){
        this.id = id;
        this.accessToken = accessToken;
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }
}
