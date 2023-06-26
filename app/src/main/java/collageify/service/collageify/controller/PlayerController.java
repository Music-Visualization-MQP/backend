package collageify.service.collageify.controller;

import collageify.exceptions.NoSPApiException;
import collageify.service.collageify.entities.ProcessedCredentials;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerController {
    private ExecutorService executorService;
    private UuidController uuid;

    private Map<UUID, ProcessedCredentials> credentialsMap = Collections.synchronizedMap(new HashMap<>());
    private Map<UUID, ProcessedCredentials> expiredCredsMap = Collections.synchronizedMap(new HashMap<>());
    private SqlController sql = new SqlController();


    public PlayerController() throws NoSPApiException, SQLException {
        executorService = Executors.newFixedThreadPool(100);
        getCredentials();
        System.out.println(this.expiredCredsMap.values().hashCode());
        System.out.println(this.credentialsMap.values().hashCode());


    }
    private void getCredentials() throws SQLException, NoSPApiException {
        ResultSet resultSet = sql.getAuthCredentials().get();
        System.out.println(resultSet.getMetaData());

        while (resultSet.next()) {
            ProcessedCredentials creds = new ProcessedCredentials(
                    uuid.generateUUID(),
                    resultSet.getInt("id"),
                    resultSet.getString("refresh_token"),
                    resultSet.getString("access_token"),
                    resultSet.getInt("user_id"),
                    resultSet.getDate("access_token_exp_date"),
                    resultSet.getTime("access_token_exp_time")
            );

            System.out.println(resultSet.getTime("access_token_exp_time"));
            System.out.println("hey from sql land");

            this.credentialsMap.put(creds.getUuid(), creds);
            System.out.println(this.credentialsMap.get(creds.getUuid()).getAccessTokenExpDate());
        }

    }
    private void filterExpiredCredentials() {
        Date now = new Date();
        for (ProcessedCredentials creds : this.credentialsMap.values()) {
            if (creds.getAccessTokenExpDate().before(now) ||
                    (creds.getAccessTokenExpDate().equals(now) && creds.getAccessTokenExpTime().before(new Time(now.getTime())))) {
                expiredCredsMap.put(creds.getUuid(), creds);
            }
        }
        credentialsMap.keySet().removeAll(expiredCredsMap.keySet());
    }

    private void getNewTokens() throws SQLException, NoSPApiException, IOException, SpotifyWebApiException {
        if(!this.expiredCredsMap.isEmpty()){
            for (ProcessedCredentials credentials: this.expiredCredsMap.values()){
                SpotifyApiModule spotify = new SpotifyApiModule(credentials);
                credentials.setAccessToken(Optional.of(spotify.getNewAccessToken().orElseThrow()));
                System.out.print(credentials.getAccessToken());

            }
        }
    }
}
