package collageify.collageify.controller;

import collageify.collageify.entities.SpotifyUserCredentials;
import collageify.web.exceptions.NoSPApiException;
import org.eclipse.jetty.util.security.Credential;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SpotifyCredentialsController {

    private SqlController sql = new SqlController();
    private SpotifyCredentialManager spotify = new SpotifyCredentialManager();
    private Map<Integer, SpotifyUserCredentials> credentialsMap = Collections.synchronizedMap(new HashMap<>());
    private Map<Integer, SpotifyUserCredentials> tmpCredentialsMap = Collections.synchronizedMap(new HashMap<>());
    private Queue<SpotifyUserCredentials> tokenRefreshQueue = new ConcurrentLinkedQueue<>();
    public SpotifyCredentialsController() throws SQLException, NoSPApiException, IOException, SpotifyWebApiException {
        tmpCredentialsMap = this.sql.getAuthCredentials();
        initCredentialMap();
    }
    private void initCredentialMap() throws IOException, SpotifyWebApiException {
        synchronized (this) {
            for (Integer i : this.tmpCredentialsMap.keySet()) {
                if (this.tmpCredentialsMap.get(i).isValid()) {
                    this.credentialsMap.put(i, this.tmpCredentialsMap.get(i));
                } else {
                    Optional<SpotifyUserCredentials> refreshedCredentials = spotify.getNewAccessToken(tmpCredentialsMap.get(i));
                    refreshedCredentials.ifPresent(credentials -> {
                        if (credentials.isValid()) {
                            this.credentialsMap.put(i, credentials);
                        } else {
                            throw new RuntimeException(String.valueOf(credentials));
                        }
                    });
                }
            }
            if (this.tmpCredentialsMap.keySet().size() == this.credentialsMap.keySet().size()) {
                this.tmpCredentialsMap.clear();
            } else {
                throw new RuntimeException("error in initialization");
            }
        }
    }

    void initRefreshQueue(){
        for(Integer i : this.credentialsMap.keySet()){

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




}
