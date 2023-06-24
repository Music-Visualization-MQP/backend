package collageify.service.collageify.controller;

import collageify.db.IDBAccess;
import collageify.db.SQLAccess;
import collageify.exceptions.NoSPApiException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class SqlController extends SQLAccess implements IDBAccess  {
    SqlController(){
        super();
    }
    @Override
    public Optional<ResultSet> getAuthCredentials() throws SQLException, NoSPApiException {
        estConnection();
        try{
            Connection connect = null;
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM spotify_credentials");
            //preparedStatement.setInt(1,(int) userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(resultSet);
                /*return  Optional.of(new Credentials((Integer) resultSet.getInt("id"),
                        resultSet.getString("refresh_token"),
                        resultSet.getString("access_token"),
                        (Integer) resultSet.getInt("user_id"),
                        resultSet.getDate("access_token_exp_date"),
                        resultSet.getTime("access_token_exp_time")));*/
            } else {
                throw new NoSPApiException("");
            }


        } catch(Exception e) {
            throw e;
        } finally {
            close();
        }


    }

}
