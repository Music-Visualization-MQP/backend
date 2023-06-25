package collageify.service.collageify.controller;

import collageify.exceptions.NoSPApiException;
import collageify.service.collageify.entities.ProcessedCredentials;

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

    }
    private void getCredentials() throws SQLException, NoSPApiException {
        ResultSet resultSet = sql.getAuthCredentials().get();
        while(resultSet.next()){
            ProcessedCredentials creds = new ProcessedCredentials(uuid.generateUUID(),(Integer) resultSet.getInt("id"),
                    resultSet.getString("refresh_token"),
                    resultSet.getString("access_token"),
                    (Integer) resultSet.getInt("user_id"),
                    resultSet.getDate("access_token_exp_date"),
                    resultSet.getTime("access_token_exp_time"));
            this.credentialsMap.put(creds.getUuid(), creds);
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

    private void getNewTokens(){
        if(!this.expiredCredsMap.isEmpty()){
            for (ProcessedCredentials credentials: this.expiredCredsMap.values()){


            }
        }
    }
}
