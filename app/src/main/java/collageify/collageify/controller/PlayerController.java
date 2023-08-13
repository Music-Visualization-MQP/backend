package collageify.collageify.controller;

import collageify.collageify.entities.Player;
import collageify.collageify.entities.SpotifyUserCredentials;
import collageify.collageify.service.CollageifyService;
import collageify.web.exceptions.JSONNotPresent;
import collageify.web.exceptions.NoSPApiException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerController {
    private ExecutorService executorService;
    private UuidController uuid = new UuidController();
    private SpotifyApiController spotify = new SpotifyApiController();


    public  Map<UUID, SpotifyUserCredentials> credentialsMap = Collections.synchronizedMap(new HashMap<>());
    public Map<UUID, SpotifyUserCredentials> expiredCredsMap = Collections.synchronizedMap(new HashMap<>());
    private SqlController sql = new SqlController();


    public PlayerController() throws NoSPApiException, SQLException {
        executorService = Executors.newFixedThreadPool(100);
        sql.getAuthCredentials() -
        //System.out.println(this.expiredCredsMap.values().hashCode());
        //System.out.println(this.credentialsMap.values().hashCode());


    }
    /*private void getCredentials() throws SQLException, NoSPApiException {
        ResultSet resultSet = sql.getAuthCredentials().get();
        System.out.println(resultSet.getMetaData());

        while (resultSet.next()) {
            SpotifyUserCredentials creds = new SpotifyUserCredentials(
                    resultSet.getInt("id"),
                    resultSet.getString("refresh_token"),
                    resultSet.getString("access_token"),
                    resultSet.getInt("user_id"),
                    resultSet.getDate("access_token_exp_date"),
                    resultSet.getTime("access_token_exp_time"),
                    SpotifyApi.builder().build()
            );

            System.out.println(resultSet.getTime("access_token_exp_time"));
            System.out.println("hey from sql land");

            this.credentialsMap.put(creds.getUuid(), creds);
            System.out.println(this.credentialsMap.get(creds.getUuid()).getAccessTokenExpDate());
        }*/

    }
    public void filterExpiredCredentials() {
        Date now = new Date();
        for (SpotifyUserCredentials creds : this.credentialsMap.values()) {
            if (creds.getAccessTokenExpDate().before(now) ||
                    (creds.getAccessTokenExpDate().equals(now) && creds.getAccessTokenExpTime().before(new Time(now.getTime())))) {
                System.out.println("invalid creds found");
                this.expiredCredsMap.put(creds.getUuid(), creds);
                this.credentialsMap.remove(creds.getUuid());
            }
        }
        this.credentialsMap.keySet().removeAll(this.expiredCredsMap.keySet());
    }

    public void getNewTokens() throws SQLException, NoSPApiException, IOException, SpotifyWebApiException {
        if(!this.expiredCredsMap.isEmpty()){
            for (SpotifyUserCredentials credentials: this.expiredCredsMap.values()){
                credentials.setAccessToken(Optional.of(this.spotify.getNewAccessToken(credentials).orElseThrow()));
                System.out.println("should be updated");
            }
            this.credentialsMap.putAll(this.expiredCredsMap);
            this.expiredCredsMap.clear();
        }
    }
    private void init() throws SQLException, NoSPApiException, IOException, SpotifyWebApiException, InterruptedException {
        Thread thread0 = new Thread(() -> {
            try {
                getCredentials();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NoSPApiException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread1 = new Thread(this::filterExpiredCredentials);
        Thread thread2 = new Thread(() ->
        {
            try {
                getNewTokens();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SpotifyWebApiException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NoSPApiException e) {
                throw new RuntimeException(e);
            }
        });
        thread0.start();
        thread0.join();
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();


    }
/*public void run() throws Exception, NoSPApiException, JSONNotPresent {
        CollageifyService collageify = new CollageifyService();
        init();
        System.out.println("initialized");

        for (SpotifyUserCredentials credentials : this.credentialsMap.values()) {
            System.out.println("first loop");
            this.players.put(credentials.getUuid(), new Player(this.spotify, credentials));
        }
        boolean shouldExit = false;

        while (!shouldExit) {
            for (Player player : this.players.values()) {
                player.run();
            }
        }
    }*/

    public void run () throws Exception, NoSPApiException, JSONNotPresent {
        init();
        CollageifyService collageify = new CollageifyService();


        System.out.println("Initialized");

        List<Callable<Void>> playerTasks = new ArrayList<>();
        for (SpotifyUserCredentials credentials : this.credentialsMap.values()) {
            playerTasks.add(() -> {
                System.out.println("First loop");
                try {

                    Player player = new Player(this.spotify, credentials);
                    return null;


                } catch (NoSPApiException e) {

                    throw new RuntimeException(e);
                }
            });
        }

        boolean shouldExit = false;

        while (!shouldExit) {
            try {
                executorService.invokeAll(playerTasks);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
                break;
            }
        }
    }
}
