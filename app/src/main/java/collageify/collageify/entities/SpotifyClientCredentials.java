package collageify.collageify.entities;

import collageify.collageify.controller.SpotifyApiController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;


public class SpotifyClientCredentials implements ISpotifyUserCredentials {

    /**
     * TODO: make private setters for these fields
     */
    final private Integer id;
    final private String refreshToken;
    private String accessToken;
    final private Integer userID;
    private Date accessTokenExpDate;
    private Time accessTokenExpTime;
    private SpotifyClientCredentialManagementStrategy strategy;

    /**
     *
     * @param id integer representing id in the table of spotify credentials
     * @param refreshToken string representing token used for fetching new access token when needed
     * @param accessToken string which is always invalid when pulled from the database since it is never updated
     * @param userID integer representing the user's id within the database
     * @param accessTokenExpDate sql date object representing the date at which the token expires
     * @param accessTokenExpTime sql time object representing the time at which the token will expire
     */
    public SpotifyClientCredentials(Integer id, String refreshToken, String accessToken, Integer userID, Date accessTokenExpDate, Time accessTokenExpTime, SpotifyApiController spotify) throws IOException, SpotifyWebApiException {
        this.id = id;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.userID = userID;
        this.accessTokenExpDate = accessTokenExpDate;
        this.accessTokenExpTime = accessTokenExpTime;
        if(isValid()){
            this.strategy = new SpotifyClientCredentialGathererStrategy();
            this.strategy.handleCredentials(this, spotify);
        } else {
            this.strategy = new SpotifyClientCredentialRefresherStrategy();
            this.strategy.handleCredentials(this, spotify);
        }

    }

    //getters


    @Override
    public Integer getId() {
        return id; }
    @Override
    public String getRefreshToken() { return refreshToken; }
    @Override
    public String getAccessToken() { return accessToken; }
    @Override
    public Integer getUserId() { return userID; }
    @Override
    public Date getAccessTokenExpDate() { return accessTokenExpDate; }
    @Override
    public Time getAccessTokenExpTime() { return accessTokenExpTime; }

    /*public void setAccessToken(Optional<SpotifyRefreshCredentials> accessToken) throws NoSPApiException {
        if(accessToken.isPresent()){
            SpotifyRefreshCredentials refresh = accessToken.get();
            this.accessToken = refresh.accessToken;
            this.accessTokenExpDate = refresh.accessTokenExpDate;
            this.accessTokenExpTime = refresh.accessTokenExpTime;

        }else throw new NoSPApiException("set access token fault");
    }*/
    @Override
    public Boolean isValid() {
        long millis = System.currentTimeMillis();
        //do we really want this 5 minute delay or whatever I understand it purpose, but it may prove un nescicary

        return millis < this.accessTokenExpDate.getTime() - 300 * 1000;
        //return millis < this.accessTokenExpDate.getTime(); this should be somewhere else


    }

    public void setAccessToken(String token){
        this.accessToken = token;

    }

    public void setAccessTokenExpDate(Date date){
        this.accessTokenExpDate = date;

    }
    public void setAccessTokenExpTime(Time time){
        this.accessTokenExpTime = time;
    }

    public String getStrategyName(){ return this.strategy.getStrategyName(); }

    public void setStrategy(SpotifyClientCredentialManagementStrategy strategy){ this.strategy = strategy; }

    /**
     *
     * @param spotify an instance of the spotify api controller is passed and handled by the strategy
     * @throws IOException
     * @throws SpotifyWebApiException
     */
    public void action(SpotifyApiController spotify) throws IOException, SpotifyWebApiException {
        String g = "gatherer";
        String r = "refresher";
        if(this.isValid() && this.strategy.getStrategyName().equals(g)){
            strategy.handleCredentials(this, spotify);
        } else if(this.isValid() && !this.strategy.getStrategyName().equals(g)){
            this.setStrategy(new SpotifyClientCredentialGathererStrategy());
            this.strategy.handleCredentials(this, spotify);
        } else if (!this.isValid() && this.strategy.getStrategyName().equals(g)){
            this.setStrategy(new SpotifyClientCredentialRefresherStrategy());
            this.strategy.handleCredentials(this, spotify);
        } else if (!this.isValid() && !this.strategy.getStrategyName().equals(g)){
            this.strategy.handleCredentials(this, spotify);

        }

    }
    //setters
/*    public void setUuid(UUID uuid) { this.uuid = uuid; }
    public void setId(Integer id) { this.id = id; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public void setUserID(Integer userID) { this.userID = userID; }
    public void setAccessTokenExpDate(Date accessTokenExpDate) { this.accessTokenExpDate = accessTokenExpDate; }
    public void setAccessTokenExpTime(Time accessTokenExpTime) { this.accessTokenExpTime = accessTokenExpTime; }*/
}