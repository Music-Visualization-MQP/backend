package collageify.collageify.controller.spotify;

import collageify.collageify.controller.SpotifyApiController;
import collageify.collageify.controller.spotify.SpotifyCredentialLoader;
import collageify.collageify.db.SQLAccess;
import collageify.collageify.entities.SpotifyClientCredentials;
import collageify.collageify.entities.SpotifyRefreshCredentials;
import collageify.web.exceptions.NoSPApiException;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SpotifyCredentialsController {

    private SpotifyCredentialLoader sql = new SpotifyCredentialLoader();
    private SpotifyApiController spotify = new SpotifyApiController();
    private SQLAccess sqlAccess = new SQLAccess();
    private Map<Integer, SpotifyClientCredentials> credentialsMap = Collections.synchronizedMap(new HashMap<>());
    private Queue<SpotifyClientCredentials> tokenRefreshQueue = new ConcurrentLinkedQueue<>();
    public SpotifyCredentialsController() throws SQLException, NoSPApiException, IOException, SpotifyWebApiException, ParseException {
        credentialsMap = this.sql.getAuthCredentials(this.spotify);
        initCredentialMap();
    }
    private void initCredentialMap() throws IOException, SpotifyWebApiException, NoSPApiException, ParseException {
        synchronized (this) {
            for (Integer i : this.credentialsMap.keySet()) {
                SpotifyClientCredentials credential = this.credentialsMap.get(i);
                if (credential.isValid() && credential.equals("gatherer")) {
                    credential.action(this.spotify, sqlAccess);

                } else if(credential.isValid() && !credential.equals("gatherer")) {
                    /*Optional<SpotifyRefreshCredentials> refreshedCredentials = spotify.getNewAccessToken(credentialsMap.get(i));
                    refreshedCredentials.ifPresent(credentials -> {
                        if (credential.isValid()) {

                            //this.credentialsMap.put(i, credentials);
                        } else {
                            throw new RuntimeException(String.valueOf());
                        }
                    });*/
                }
            }
            /*
            * this can get deleted i just need to make sure it wasnt doing something fucking weird
            if (this.credentialsMap.keySet().size() == this.credentialsMap.keySet().size()) {
                this.credentialsMap.clear();
            } else {
                throw new RuntimeException("error in initialization");
            }*/
        }
    }



    public void test() {
        System.out.println(credentialsMap.keySet());
        for(Integer I : credentialsMap.keySet()){
            System.out.println(credentialsMap.get(I).getAccessTokenExpDate());
        }
    }

    /**
     *
     * @return size of the active credentials map
     */
    public int keysInSet(){
        return this.credentialsMap.keySet().size();
    }
    public boolean areKeysValid(){
        for(Integer i : this.credentialsMap.keySet()){
            if(!this.credentialsMap.get(i).isValid()){
                return false;
            }
        }
        return true;
    }
    void updateSpotifyCredentials(SpotifyClientCredentials credentials){
        Integer id = credentials.getId();
        if(this.credentialsMap.containsKey(id)){
            this.credentialsMap.remove(id);
            this.credentialsMap.put(id, credentials);
        } else {
            credentialsMap.put(id, credentials);
        }
    }




}
