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

import collageify.user.User;

public class MySQLAccess implements IDBAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private Optional<Integer> getNextID() throws Exception {  
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

    }

    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(System.getenv("DB_ADDRESS"), System.getenv("DB_USER"), System.getenv("DB_PW"));
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            statement.executeQuery(null);
            // Result set get the result of the SQL query
            
/*             resultSet = statement
                    .executeQuery("select * from feedback.comments");
            writeResultSet(resultSet);

            // PreparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement("INSERT INTO  users('user_id') VALUES('2')");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "TestEmail");
            preparedStatement.setString(3, "TestWebpage");
            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
            preparedStatement.setString(5, "TestSummary");
            preparedStatement.setString(6, "TestComment");
            preparedStatement.executeUpdate();

            preparedStatement = connect
                    .prepareStatement("SELECT user_id FROM users");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);

            // Remove again the insert comment
            preparedStatement = connect
            .prepareStatement("delete from feedback.comments where myuser= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();

            resultSet = statement
            .executeQuery("select * from feedback.comments");
            writeMetaData(resultSet); */

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String user = resultSet.getString("myuser");
            String website = resultSet.getString("webpage");
            String summary = resultSet.getString("summary");
            Date date = resultSet.getDate("datum");
            String comment = resultSet.getString("comments");
            System.out.println("User: " + user);
            System.out.println("Website: " + website);
            System.out.println("summary: " + summary);
            System.out.println("Date: " + date);
            System.out.println("Comment: " + comment);
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
                int playID = getNextID().orElseThrow(() -> new IllegalStateException("Value is not present"));
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
    public void addUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUser'");
    }

}
