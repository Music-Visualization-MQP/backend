package collageify.collageify.entities;
import collageify.collageify.controller.SpotifyApiController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.Optional;

public class SpotifyClientCredentialRefresherStrategy implements SpotifyClientCredentialManagementStrategy {

    /**
     * this class is partially implemented the idea is that when action is called the credential is updated
     * <p>
     * <p>
     * TODO: implement a spotify stuff
     */

    @Override
    public void handleCredentials(SpotifyClientCredentials credentials, SpotifyApiController spotify) throws IOException, SpotifyWebApiException {
        Optional<SpotifyRefreshCredentials> response = spotify.getNewAccessToken(credentials);
        if(response.isPresent()){
            credentials.setAccessToken(response.get().getAccessToken());
            credentials.setAccessTokenExpDate(response.get().getAccessTokenExpDate());
            credentials.setAccessTokenExpTime(response.get().getAccessTokenExpTime());
        }

    }

    @Override
    public String getStrategyName() {
        return "refresher";
    }
}
