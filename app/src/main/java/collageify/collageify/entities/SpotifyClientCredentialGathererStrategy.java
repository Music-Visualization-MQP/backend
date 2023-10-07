package collageify.collageify.entities;

import collageify.collageify.controller.SpotifyApiController;
import collageify.collageify.db.SQLAccess;
import collageify.web.exceptions.NoSPApiException;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Future;

public class SpotifyClientCredentialGathererStrategy implements SpotifyClientCredentialManagementStrategy {

    @Override
    public void handleCredentials(SpotifyClientCredentials credentials, SpotifyApiController spotify, SQLAccess sqlAccess) throws NoSPApiException, IOException, ParseException, SpotifyWebApiException {
        Optional<String> apiResponse = spotify.requestData(credentials);


        apiResponse.ifPresent(System.out::println);
    }

    @Override
    public String getStrategyName() {
        return "gatherer";
    }

}
