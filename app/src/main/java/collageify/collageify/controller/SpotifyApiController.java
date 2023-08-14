package collageify.collageify.controller;
import collageify.collageify.entities.SpotifyUserCredentials;
import collageify.web.exceptions.NoSPApiException;
import collageify.collageify.entities.RefreshCredentials;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.util.Optional;

public class SpotifyApiController {

    //private final Integer id;
    private final Optional<SpotifyApi> spotifyApi = Optional.empty();
    private SpotifyUserCredentials credentials;

    /**
     * This is the constructor, and thus it is side effect only, it consumes a SpotifyUserCredentials object,
     * and stores it in a private field
     *
     * @throws NoSPApiException catch all for issues related to the spotify api
     * @throws SQLException thrown given any issues connecting to the database
     */
    public SpotifyApiController(){
    }
    /*public SpotifyApiController(SpotifyUserCredentials credentials) throws NoSPApiException, SQLException{
        this.credentials = credentials;
        //this.spotifyApi = spotifyApi(accessToken, refreshToken);
    }*/
    private static final String clientId = System.getenv("SP_CID");
    private static final String clientSecret = System.getenv("SP_S");
    private Optional<SpotifyApi> spotifyApi(Optional<SpotifyUserCredentials> credentials) throws NoSPApiException {
        final Optional<SpotifyApi> returnVal;
        if(credentials.isPresent()){
            returnVal = Optional.of(new SpotifyApi.Builder()
                    .setAccessToken(credentials.get().getAccessToken())
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
    public Optional<String> requestData(SpotifyUserCredentials credentials) throws NoSPApiException, IOException, ParseException, SpotifyWebApiException {
        final Optional<SpotifyApi> api =  spotifyApi(Optional.of(credentials));
        final Optional<GetUsersCurrentlyPlayingTrackRequest> request = Optional.of(api.get().getUsersCurrentlyPlayingTrack().build());

        return request.isPresent() ? Optional.ofNullable(request.get().getJson()) : Optional.empty();
        /*if(this.spotifyApi.isPresent()){

            final Optional<GetUsersCurrentlyPlayingTrackRequest> request = Optional.of(this.getSpotifyApi().getUsersCurrentlyPlayingTrack().build());

            return request.isPresent() ? Optional.ofNullable(request.get().getJson()) : Optional.empty();
        }else throw new NoSPApiException("opps");*/

    }
    public Optional<SpotifyUserCredentials> getNewAccessToken(SpotifyUserCredentials credentials) throws IOException, SpotifyWebApiException{
        AuthorizationCodeRefreshRequest request = SpotifyApi.builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(credentials.getRefreshToken())
                .build().authorizationCodeRefresh().build();
        try{
            AuthorizationCodeCredentials refreshCredentials = request.execute();
            long millis = Instant.now().toEpochMilli() + (refreshCredentials.getExpiresIn() * 1000);
            return Optional.of(new SpotifyUserCredentials(credentials.getId(), credentials.getRefreshToken(), refreshCredentials.getAccessToken(), credentials.getUserId(), new Date(millis), new Time(millis))) ;
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
