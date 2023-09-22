package collageify.collageify.controller.spotify;

import collageify.collageify.controller.SpotifyApiController;
import collageify.collageify.entities.SpotifyClientCredentials;
import collageify.web.exceptions.NoSPApiException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SpotifyCredentialLoader {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    SpotifyCredentialLoader(){
    }
    private void estConnection() throws SQLException {
        try {
            // This will load the MySQL driver, each DB has its own driver
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(System.getenv("DB_ADDRESS"), System.getenv("DB_USER"), System.getenv("DB_PW"));

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *
     *  Side Effect: This method establishes a database connection ond once the credentials are placed in the map..
     *  the connection is terminated with the close() method
     * @return This method produces a synchronized map of all spotify client credentials that exist inside the database
     *
     * TODO: Do something when there are non, perhaps log something
     * @throws SQLException
     * @throws NoSPApiException
     */
    public Map<Integer, SpotifyClientCredentials> getAuthCredentials(SpotifyApiController spotify) throws SQLException, NoSPApiException, IOException, SpotifyWebApiException {
        estConnection();
        Map<Integer, SpotifyClientCredentials> credentialsMap = Collections.synchronizedMap(new HashMap<>());
        try{
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM spotify_credentials");
            //preparedStatement.setInt(1,(int) userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String refreshToken = resultSet.getString("refresh_token");
                String accessToken = resultSet.getString("access_token");
                Integer userID = resultSet.getInt("user_id");
                Date accessTokenExpDate = resultSet.getDate("access_token_exp_date");
                Time accessTokenExpTime = resultSet.getTime("access_token_exp_time");

                SpotifyClientCredentials credentials = new SpotifyClientCredentials(
                        id, refreshToken, accessToken, userID, accessTokenExpDate, accessTokenExpTime,spotify);

                credentialsMap.put(id, credentials);
            }
            close();

            return  credentialsMap;
            /*if(resultSet.next()){
                System.out.println(resultSet.getInt("id"));
                *//*return  Optional.of(new Credentials((Integer) resultSet.getInt("id"),
                        resultSet.getString("refresh_token"),
                        resultSet.getString("access_token"),
                        (Integer) resultSet.getInt("user_id"),
                        resultSet.getDate("access_token_exp_date"),
                        resultSet.getTime("access_token_exp_time")));*//*
            } else {
                throw new NoSPApiException("");
            }*/


        } catch(Exception e) {
            throw e;
        }


    }

    /**
     * This funtion is side effect only and it closes out any data base conenction
     */
    protected void close() {
        try {

            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}
