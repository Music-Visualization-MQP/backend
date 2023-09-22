/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package collageify;

import collageify.collageify.controller.SpotifyApiController;
import collageify.collageify.controller.spotify.SpotifyCredentialsController;
import collageify.collageify.entities.SpotifyClientCredentials;
import collageify.web.exceptions.NoSPApiException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

class CollageifyTests {
    //public SpotifyCredentialsController spotify;

    long millis;
    public SpotifyClientCredentials testCredentials;

    CollageifyTests() throws SQLException, NoSPApiException, IOException, SpotifyWebApiException {
        millis = System.currentTimeMillis();
        SpotifyApiController spotify = new SpotifyApiController();
        testCredentials = new SpotifyClientCredentials(222, "abc", "def", 222, new Date(millis), new Time(millis),spotify);
    }
    public
    @Test void testIsTokenValidate(){
        assertTrue(testCredentials.isValid());
    }
    public
    @Test void testGrabKeysandValidate() throws SQLException, NoSPApiException, IOException, SpotifyWebApiException {
        /*spotify = new SpotifyCredentialsController();
        assertEquals(spotify.keysInSet(),3);
        assertTrue(spotify.areKeysValid());*/
    }


}
