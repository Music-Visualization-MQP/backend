package collageify.collageify.controller;

import collageify.collageify.db.IDBAccess;
import collageify.collageify.db.SQLAccess;
import collageify.collageify.entities.SpotifyUserCredentials;
import collageify.web.exceptions.NoSPApiException;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SqlController  {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    SqlController(){
    }
    public void estConnection() throws SQLException {
        try {
            // This will load the MySQL driver, each DB has its own driver
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(System.getenv("DB_ADDRESS"), System.getenv("DB_USER"), System.getenv("DB_PW"));

        } catch (Exception e) {
            throw e;
        }
    }
    //this is like optional hell
    public Map<Integer, SpotifyUserCredentials> getAuthCredentials() throws SQLException, NoSPApiException {
        estConnection();
        Map<Integer, SpotifyUserCredentials> credentialsMap = Collections.synchronizedMap(new HashMap<>());
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

                SpotifyUserCredentials credentials = new SpotifyUserCredentials(
                        id, refreshToken, accessToken, userID, accessTokenExpDate, accessTokenExpTime);

                credentialsMap.put(id, credentials);
            }

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
        } finally {
            close();
        }


    }
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
