package collageify.collageify.db;
import java.sql.*;
import java.util.LinkedList;
import java.util.Optional;

import collageify.collageify.entities.SpotifyUserCredentials;
import collageify.web.exceptions.NoSPApiException;

public class SQLAccess implements IDBAccess {
    private PreparedStatement preparedStatement = null;
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    // You need to close the resultSet

  /**
     * @throws SQLException
     * This is for side effect only
     *
     * the side effect of the close() is to close the connection with the
     * database if any of the values are not null they are closed using
     * the close function definied by their classes
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

/**
     *
     * @throws SQLException
     * This method is for side effect only
     *
     * Its side effect is to conect to the database by changing the state of the connect variable
     */

    @Override
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

    @Override
    public void addPlayed(Integer userID, String username, String spURI, String artistName, String albumName, String trackName, Integer popularity, Integer durationMS, String json) throws Exception {
        try{
            SQLTime dateTime = new SQLTime();
            preparedStatement = connect.prepareStatement("INSERT INTO played (user_id, play_date, play_time, spotify_uri, artist_name, album_name, track_name, popularity, duration_ms) VALUES (?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, (int) userID);
            preparedStatement.setDate(2, dateTime.getDate());
            preparedStatement.setTime(3, dateTime.getTime());
            preparedStatement.setString(4, spURI);
            preparedStatement.setString(5, artistName);
            preparedStatement.setString(6, albumName);
            preparedStatement.setString(7, trackName);
            preparedStatement.setInt(8, (int) popularity);
            preparedStatement.setInt(9, (int) durationMS);
            //preparedStatement.setString(10, json);
            System.out.println("i got to 94!");
            try{
                preparedStatement.executeUpdate();

            } catch (Exception e){
                throw e;
            }
            System.out.println("should be in there");
            //preparedStatement = connect.prepareStatement("INSERT INTO played (play_id, user_id)")
        }catch (Exception e){
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public void addSpotifyCredentials(String email, String accessToken, String refreshToken, long accessTokenExp) throws SQLException {

        Date date = new Date(accessTokenExp);
        Time time = new Time(accessTokenExp);
        System.out.println("125 in sql access");
        try{
            Optional<Integer> optUserId = getUserIdByEmail(email);
            Integer userId = null;
            if(optUserId.isPresent()){
                userId = optUserId.get();
                preparedStatement = connect.prepareStatement("INSERT INTO spotify_credentials (refresh_token, access_token, user_id, access_token_exp_date, access_token_exp_time) VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE "+
                        "refresh_token = VALUES(refresh_token), " +
                        "access_token = VALUES(access_token), " +
                        "access_token_exp_date = VALUES(access_token_exp_date), " +
                        "access_token_exp_time = VALUES(access_token_exp_time)");
                preparedStatement.setString(1, refreshToken);
                preparedStatement.setString(2, accessToken);
                preparedStatement.setInt(3, (int) userId);
                preparedStatement.setDate(4, date);
                preparedStatement.setTime(5,time);
                preparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("userid could not be instantiated");
            }


        } catch(Exception e) {
            throw e;
        } finally {
            close();
        }

    }


    @Override
    public void getTrackPlayedUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTrackPlayedUser'");
    }

    @Override
    public void getTrackPlayedPublic() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTrackPlayedPublic'");
    }

    @Override
    public LinkedList<SpotifyUserCredentials> getAuthCredentials() throws SQLException, NoSPApiException {
        return new LinkedList<>();
    }

/**
     *
     * @param email The method consumes a string represent the email of the user who's...
     *             spotify information is being looked up, eventually username should also work
     * @return This method produces a
     * @throws SQLException
*/


    public Optional<Integer> getUserIdByEmail(String email) throws SQLException {
        try {
            estConnection();
            PreparedStatement statement0 = connect.prepareStatement("SELECT user_id FROM users WHERE email = ?");
            statement0.setString(1, email);
            ResultSet result0 = statement0.executeQuery();
            if(result0.next()) {
                return Optional.of(result0.getInt("user_id"));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw e;
        }

    }

    public Boolean checkIfCredsExistByEmail(String email) throws SQLException {
        try{
            estConnection();
            PreparedStatement statement0 = connect.prepareStatement("SELECT user_id FROM users WHERE email = ?");
            statement0.setString(1, email);
            ResultSet result0 = statement0.executeQuery();
            if(result0.next()){
                PreparedStatement statement1 = connect.prepareStatement("SELECT id FROM spotify_credentials WHERE user_id = ?");
                statement1.setInt(1, result0.getInt("user_id"));
                ResultSet result1 = statement1.executeQuery();
                if(result1.next()){
                    return true;
                }else{
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e){
            throw e;
        } finally {
            close();
        }
    }
}
