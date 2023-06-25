package collageify.service.collageify.controller;
import collageify.exceptions.NoSPApiException;
import collageify.service.collageify.entities.ProcessedCredentials;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class SpotifyApiModule {

    //private final Integer id;
    private final Optional<SpotifyApi> spotifyApi = Optional.empty();
    private ProcessedCredentials credentials;
    public SpotifyApiModule(ProcessedCredentials credentials) throws NoSPApiException, SQLException{



        //this.spotifyApi = spotifyApi(accessToken, refreshToken);
    }
    private static final String clientId = System.getenv("SP_CID");
    private static final String clientSecret = System.getenv("SP_S");
    private Optional<SpotifyApi> spotifyApi(Optional<String> accessToken, Optional<String> refreshToken) throws NoSPApiException {
        final Optional<SpotifyApi> returnVal;
        if(accessToken.isPresent() && refreshToken.isPresent()){
            returnVal = Optional.of(new SpotifyApi.Builder()
                    .setRefreshToken(refreshToken.get())
                    .setAccessToken(accessToken.get())
                    .build());
            System.out.print("built api");

        } else {
            throw new NoSPApiException("cannot connect to spotify in any capacity");
        }

        return returnVal;
    }
    public SpotifyApi getSpotifyApi() throws NoSPApiException {
        if(this.spotifyApi.isPresent()){
            return this.spotifyApi.get();
        } else throw new NoSPApiException("invalid spotify api");
    }
    public Optional<String> requestData() throws NoSPApiException, IOException, ParseException, SpotifyWebApiException {
        if(this.spotifyApi.isPresent()){

            final Optional<GetUsersCurrentlyPlayingTrackRequest> request = Optional.of(this.getSpotifyApi().getUsersCurrentlyPlayingTrack().build());

            return request.isPresent() ? Optional.ofNullable(request.get().getJson()) : Optional.empty();
        }else throw new NoSPApiException("opps");

    }

    public Optional<String> getNewAccessToken() throws IOException, SpotifyWebApiException{
        AuthorizationCodeRefreshRequest request = SpotifyApi.builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(this.credentials.getRefreshToken())
                .build().authorizationCodeRefresh().build();
        try{
            AuthorizationCodeCredentials credentials = request.execute();
            return Optional.of(credentials.getAccessToken());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }




    /*private SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(Optional.of(accessToken))
            .build();*/

    /*private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();*/


}
