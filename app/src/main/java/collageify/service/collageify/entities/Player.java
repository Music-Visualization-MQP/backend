package collageify.service.collageify.entities;
import collageify.db.SQLAccess;
import collageify.exceptions.JSONNotPresent;
import collageify.exceptions.NoSPApiException;
import collageify.service.collageify.CollageifyService;
import collageify.service.collageify.controller.SpotifyApiController;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;


public class Player {
    private boolean initialized;
    private Integer userID = 8;
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


    public SpotifyApiController spAccess;

    public Player(ProcessedCredentials credentials) throws NoSPApiException, SQLException {
        this.spAccess = new SpotifyApiController(credentials);
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
            this.initialized = true;
            this.progressMS = node.get().get("progress_ms").asInt();
            this.durationMS = node.get().get("item").get("duration_ms").asInt();
            this.isrc = node.get().get("item").get("external_ids").get("isrc").asText();
            this.username = node.get().get("context").get("uri").asText();
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

    private void UpdateDB() throws Exception{
        System.out.println(this.enoughPlayed);
        if(this.enoughPlayed){
            SQLAccess  sql = new SQLAccess();
            try{
                System.out.println("updating db");
                sql.estConnection();
                System.out.println("connected");
                sql.addPlayed(8, username, spURI, artistName, albumName, trackName, popularity, durationMS, this.json.asText());
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
    public void run() throws Exception, NoSPApiException, JSONNotPresent {
        CollageifyService customService = new CollageifyService();

        /*while (this.spAccess.credentials.get().isTokenValid()) {

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
        }*/


        // Shut down the custom service's ExecutorService when you're done
        customService.shutdown();
    }

    /*public void run() throws Exception, NoSPApiException, JSONNotPresent{
        while (this.spAccess.credentials.get().isTokenValid()){
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
            }
        }

    }*/




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


}