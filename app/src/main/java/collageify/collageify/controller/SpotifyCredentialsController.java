package collageify.collageify.controller;

import collageify.collageify.entities.SpotifyUserCredentials;
import collageify.web.exceptions.NoSPApiException;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SpotifyCredentialsController {
    private SqlController sql = new SqlController();
    private Map<Integer, SpotifyUserCredentials> credentialsMap = Collections.synchronizedMap(new HashMap<>());
    private Map<Integer, SpotifyUserCredentials> tmpCredentialsMap = Collections.synchronizedMap(new HashMap<>());

    public SpotifyCredentialsController() throws SQLException, NoSPApiException {
        tmpCredentialsMap = sql.getAuthCredentials();
    }
    private void init(){
        synchronized (this){
            for(Integer i : tmpCredentialsMap.keySet()){
                if(!tmpCredentialsMap.get(i).isValid()){

                }
            }

        }




    }


    public void test() {
        System.out.println(credentialsMap.keySet());
        for(Integer I : credentialsMap.keySet()){
            System.out.println(credentialsMap.get(I).getAccessTokenExpDate());
        }
    }


}
