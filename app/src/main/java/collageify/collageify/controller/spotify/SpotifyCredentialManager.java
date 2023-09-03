package collageify.collageify.controller.spotify;

import collageify.collageify.controller.ISpotifyCredentialManagementStrategy;
import collageify.collageify.entities.SpotifyClientCredentials;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;


import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.util.Optional;

public class SpotifyCredentialManager implements ISpotifyCredentialManagementStrategy {
    private static final String clientId = System.getenv("SP_CID");
    private static final String clientSecret = System.getenv("SP_S");
    @Override
    public Optional<SpotifyClientCredentials> getNewAccessToken(SpotifyClientCredentials credentials) throws IOException, SpotifyWebApiException {
        AuthorizationCodeRefreshRequest request = SpotifyApi.builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(credentials.getRefreshToken())
                .build().authorizationCodeRefresh().build();
        try{
            AuthorizationCodeCredentials refreshCredentials = request.execute();
            long millis = Instant.now().toEpochMilli() + (refreshCredentials.getExpiresIn() * 1000);
            return Optional.of(new SpotifyClientCredentials(credentials.getId(), credentials.getRefreshToken(), refreshCredentials.getAccessToken(), credentials.getUserId(), new Date(millis), new Time(millis))) ;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private void delayedRefresh(){

    }

    public static void main(String[] args) {

    }
}
