package collageify.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;

import javax.swing.text.html.Option;

import collageify.auth.LoginAttempt;
import collageify.exceptions.InvalidOptionException;
import collageify.user.User;

public class MySQLAccess implements IDBAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private Optional<Integer> getNextID(int option) throws Exception {  
        try{
            if(option == 0){
                try{
                    resultSet = connect.createStatement().executeQuery("SELECT MAX(play_id) FROM played;");
                        if (resultSet.next()){
                            return Optional.of((Integer) resultSet.getInt(1) + 1);
                        } else {
                            return Optional.empty();
                        }
                } catch (Exception e){
                    throw e; 
                }
            } else if (option == 1){
                try{
                    resultSet = connect.createStatement().executeQuery("SELECT MAX(user_id) FROM users;");
                        if (resultSet.next()){
                            return Optional.of((Integer) resultSet.getInt(1) + 1);
                        } else {
                            return Optional.empty();
                        }
                } catch (Exception e){
                    throw e; 
                }
            } else {
                throw new InvalidOptionException(option);
            }
        } catch (Exception e) {
            throw e;
        }
    }
    // You need to close the resultSet
    private void close() {
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

    @Override
    public void estConnection() throws Exception {
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
    public void addPlayed(Integer userID, String username, String spURI, String artistName, String albumName, String trackName, Integer popularity, Integer durationMS) throws Exception {
        try{
            // PreparedStatements can use variables and are more efficient
            /* preparedStatement = connect
                    .prepareStatement("INSERT INTO  users('user_id') VALUES('2')");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "TestEmail");
            preparedStatement.setString(3, "TestWebpage");
            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
            preparedStatement.setString(5, "TestSummary");
            preparedStatement.setString(6, "TestComment");
            preparedStatement.executeUpdate(); */
            resultSet = connect.createStatement().executeQuery("SELECT MAX(play_id) FROM played;");
            if (resultSet.next()){
                SQLTime dateTime = new SQLTime();

                preparedStatement = connect.prepareStatement("INSERT INTO played (play_id, user_id, play_date, play_time, spotify_uri, artist_name, album_name, track_name, popularity, duration_ms) VALUES (?,?,?,?,?,?,?,?,?,?)");
                int playID = getNextID(0).orElseThrow(() -> new IllegalStateException("Value is not present"));
                preparedStatement.setInt(1, (int) playID);
                preparedStatement.setInt(2, (int) userID);
                preparedStatement.setString(3, dateTime.getDate());
                preparedStatement.setString(4, dateTime.getTime());
                preparedStatement.setString(5, spURI);
                preparedStatement.setString(6, artistName);
                preparedStatement.setString(7, albumName);
                preparedStatement.setString(8, trackName);
                preparedStatement.setInt(9, (int) popularity);
                preparedStatement.setInt(10, (int) durationMS);
                
                preparedStatement.executeUpdate();
            }
            //preparedStatement = connect.prepareStatement("INSERT INTO played (play_id, user_id)")
        }catch (Exception e){
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
    public void addUser(User user) throws Exception{
        try{
            // PreparedStatements can use variables and are more efficient
            /* preparedStatement = connect
                    .prepareStatement("INSERT INTO  users('user_id') VALUES('2')");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "TestEmail");
            preparedStatement.setString(3, "TestWebpage");
            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
            preparedStatement.setString(5, "TestSummary");
            preparedStatement.setString(6, "TestComment");
            preparedStatement.executeUpdate(); */
            resultSet = connect.createStatement().executeQuery("SELECT MAX(play_id) FROM played;");
            if (resultSet.next()){
                SQLTime dateTime = new SQLTime();

                preparedStatement = connect.prepareStatement("INSERT INTO users (user_id, username, email, password, spotify_username, creation_date) VALUES (?,?,?,?,?,?)");
                int userID = getNextID(1).orElseThrow(() -> new IllegalStateException("Value is not present"));
                preparedStatement.setInt(1, (int) userID);
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setString(5, null);
                preparedStatement.setString(6, dateTime.getDate());                
                preparedStatement.executeUpdate();
            }
            //preparedStatement = connect.prepareStatement("INSERT INTO played (play_id, user_id)")
        }catch (Exception e){
            throw e;
        } finally {
            close();
        }
        
    }

    @Override
    public void getAccount(LoginAttempt loginAttempt) throws Exception {
        String email = loginAttempt.getEmail();
        String password = loginAttempt.getPw();
        try{
            resultSet = connect.createStatement().executeQuery("SELECT COUNT(*) FROM users WHERE email = '"+ email + "';");
            if(resultSet.next()){
                System.out.println("yes");

            }else{
                System.out.println("no");

            }
        }catch(Exception e) {
            throw e;
        } finally {
            close();
        }



    }

}
