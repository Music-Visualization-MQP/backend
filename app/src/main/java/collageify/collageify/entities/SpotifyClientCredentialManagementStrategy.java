package collageify.collageify.entities;

import collageify.collageify.controller.SpotifyApiController;
import collageify.collageify.db.SQLAccess;
import collageify.web.exceptions.NoSPApiException;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

public interface SpotifyClientCredentialManagementStrategy {


    /**
     * Side Effect Only: This method will do something with the credentials, whether it is to update them, or
     * to gather data, it is up to you!
     *
     * TODO: implement this action method. one should get a new token, the other should be used to fetch data and send it to a filter class and on to the database
     */
    default void handleCredentials(SpotifyClientCredentials credentials, SpotifyApiController spotify) throws IOException, SpotifyWebApiException, NoSPApiException{

    }
    default void handleCredentials(SpotifyClientCredentials credentials, SpotifyApiController spotify, SQLAccess sql) throws NoSPApiException, IOException, ParseException, SpotifyWebApiException {

    }

    public String getStrategyName();
}
