package collageify.collageify.entities;
import collageify.collageify.controller.SpotifyApiController;
import collageify.collageify.db.SQLAccess;
import collageify.web.exceptions.JSONNotPresent;
import collageify.web.exceptions.NoSPApiException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;


public class Player implements Runnable {
    private SpotifyUserCredentials credentials;
    private Integer userID;
    private String username;

    private Integer progressMS;
    private Double progressPercent;

    private String spURI;
    private String artistName;
    private String albumName;
    private String trackName;
    private Integer popularity;
    private Integer durationMS;

    private Boolean enoughPlayed;

    private Boolean playing;
    private String isrc;
    private String oldIsrc;
    private JsonNode json;


    public SpotifyApiController spotify;

    public Player(SpotifyApiController spotify, SpotifyUserCredentials credentials) throws NoSPApiException, SQLException {
        this.credentials = credentials;
        this.spotify = spotify;
        this.userID = this.credentials.getUserId();
    }

   /* private void addInfo(Integer userID, String username, Integer progressMS, String spURI, String artistName, String albumName, String trackName, Integer popularity, Integer durationMS) throws NoSPApiException {
        this.userID = userID;
        this.username = username;
        this.progressMS = progressMS;
        this.spURI = spURI;
        this.artistName = artistName;
        this.albumName = albumName;
        this.trackName = trackName;
        this.popularity = popularity;
        this.durationMS = durationMS;
        this.progressPercent = (double) (progressMS / durationMS);
        if(this.progressPercent >= 0.66d){
            this.enoughPlayed = true;
        } else{
            this.enoughPlayed = false;
        }
        }*/

    public void initSong(Optional<JsonNode> node){
        if(node.isPresent()){
            this.progressMS = node.get().get("progress_ms").asInt();
            this.durationMS = node.get().get("item").get("duration_ms").asInt();
            this.isrc = node.get().get("item").get("external_ids").get("isrc").asText();
            this.spURI = node.get().get("item").get("uri").asText();
            this.artistName = node.get().get("item").get("artists").get(0).get("name").asText();
            this.albumName = node.get().get("item").get("album").get("name").asText();
            this.trackName = node.get().get("item").get("name").asText();
            this.popularity = node.get().get("item").get("popularity").asInt();
        }

    }

    private void UpdateProgress(Integer newProgressMS){
        this.progressMS = newProgressMS;
        this.progressPercent = (double) this.progressMS / this.durationMS;
        System.out.println(this.progressPercent);
        if(this.progressPercent >= 0.66d){
            System.out.println("enough played");
            this.enoughPlayed = true;
        } else{
            this.enoughPlayed = false;
        }
    }
// this needs to consume a db controller not make new one that's horrid
    private void UpdateDB() throws Exception{
        System.out.println(this.enoughPlayed);
        if(this.enoughPlayed){
            SQLAccess  sql = new SQLAccess();
            try{
                System.out.println("updating db");
                sql.estConnection();
                System.out.println("connected");
                sql.addPlayed(8, username, spURI, artistName, albumName, trackName, popularity, durationMS, " ");
                //sql.addPlayed(userID, username, spURI, artistName, albumName, trackName, popularity, durationMS, " ");
                System.out.println("should be in there");
            } catch (Exception e){
                throw e;
            }
        }
    }

    private Optional<JsonNode>  responseToJson(Optional<String> response) throws JsonProcessingException, JSONNotPresent {
        if(response.isPresent()){
            return Optional.ofNullable(new ObjectMapper().readTree(response.get()));
        } else {
            return Optional.empty();
        }
    }
    /*public void run() throws Exception, NoSPApiException, JSONNotPresent {
        CollageifyService customService = new CollageifyService();

        while (this.spAccess.credentials.get().isTokenValid()) {

            System.out.println("waiting");
            if (Optional.ofNullable(this.spAccess.requestData()).isPresent()) {

                //System.out.println(responseToJson(this.spAccess.requestData()).get());
                //System.out.println(responseToJson(this.spAccess.requestData()).get().get("item").get("name").asText());
                if (this.spAccess.requestData().get().isEmpty()) {
                    Thread.sleep(5000);
                    System.out.println("nothing playing");
                } else if (this.initialized == false) {
                    System.out.println("116");
                    this.initSong(responseToJson(this.spAccess.requestData()));
                    this.json = responseToJson(this.spAccess.requestData()).get();
                    System.out.println("going to sleep");
                    Thread.sleep(5000);
                } else if (this.initialized == true) {
                    System.out.println("121");
                    this.UpdateProgress(responseToJson(this.spAccess.requestData()).get().get("progress_ms").asInt());
                    System.out.println(this.enoughPlayed);
                    //unsure if this is needed
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    Future<?> future = executorService.submit(() -> {
                        try {
                            this.UpdateDB();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    future.get();
                    System.out.println(responseToJson(this.spAccess.requestData()).get().get("item").get("name").asText());
                    System.out.println(this.trackName);
                    oldIsrc = isrc;
                    this.UpdateProgress(responseToJson(this.spAccess.requestData()).get().get("progress_ms").asInt());
                    System.out.println("sleeping " + (this.durationMS - this.progressMS));

                    //System.out.println(this.durationMS);
                    //System.out.println(this.progressMS);


                    // Submit the task to the custom service's ExecutorService
                    Thread.sleep(5000);
                } else {
                    System.out.println("i got nothing");
                }

            }
        }


        // Shut down the custom service's ExecutorService when you're done
        customService.shutdown();
    }*/

    public void run(){
        //System.out.println(credentials.getAccessTokenExpTime() + " " +this.credentials.getAccessTokenExpDate());
        Optional<String> requestDataHistorical = Optional.empty();
        while(credentials.isValid()){
            try{
                System.out.println("177");
                //this should be a callable subitted to the pool then its
                Optional<String> requestData = this.spotify.requestData(credentials);
                if(credentials.getAccessTokenExpTime().getTime()
                        - (5*60*1000) < System.currentTimeMillis()){
                    System.out.println("token expiring soon");
                    //credentials.setAccessToken(Optional.of(this.spotify.getNewAccessToken(credentials).orElseThrow()));
                    System.out.println("token updated!");
                } if (requestData.isPresent() && requestDataHistorical.isEmpty()) {
                    System.out.println(requestData.get());
                    this.initSong(responseToJson(requestData));
                    requestDataHistorical = requestData;
                    synchronized (this) {
                        wait(5000);
                    }
                } else if(requestData.isPresent() && requestDataHistorical.isPresent()){
                    //
                    if(Objects.equals(responseToJson(requestData).get().get("item").get("uri").asText(),
                            responseToJson(requestDataHistorical).get().get("item").get("uri").asText())){
                        System.out.println("song has not changed");
                        this.UpdateProgress(responseToJson(requestData).get().get("progress_ms").asInt());
                        if(this.enoughPlayed){
                            UpdateDB();
                            System.out.println("Updated db waiting");
                            requestDataHistorical = Optional.empty();
                            synchronized (this){
                                wait(this.durationMS - this.progressMS);
                            }
                        }
                    } else {
                        requestDataHistorical = Optional.empty();
                    }
                }
                synchronized (this){
                    wait(5000);
                }
            } catch (NoSPApiException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (JSONNotPresent e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (SpotifyWebApiException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        /*while (this.spAccess.credentials.get().isTokenValid()){
            System.out.println("waiting");
            if(Optional.ofNullable(this.spAccess.requestData()).isPresent()){
                System.out.println(responseToJson(this.spAccess.requestData()).get());


                System.out.println(responseToJson(this.spAccess.requestData()).get().get("item").get("name").asText());
                this.initProgress(responseToJson(this.spAccess.requestData()));
                System.out.println(this.durationMS);
                System.out.println(this.progressMS);


                //System.out.println(this.spAccess.requestData());

            } else {
                System.out.println("i got nothing");
            } try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                // Handle the InterruptedException if needed
            }*/
    }

}




    /* void setUserID(Integer userID){
        this.userID = userID;
    }
    void setUserName(String username){
        this.username = username;
    }
    void setUserID(Integer userID){
        this.userID = userID;
    }
    void setUserID(Integer userID){
        this.userID = userID;
    }
    void setUserName; */

