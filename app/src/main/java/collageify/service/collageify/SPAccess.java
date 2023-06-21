package collageify.service.collageify;
import collageify.db.SQLAccess;
import collageify.exceptions.NoSPApiException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SPAccess {

    //private final Integer id;
    private final Optional<SpotifyApi> spotifyApi;
    public Optional<Credentials> credentials;

    SPAccess(Integer userId) throws NoSPApiException, SQLException{
        SQLAccess sql = new SQLAccess();
        sql.estConnection();
        this.credentials = sql.getAuthCredentials(userId);
        if(credentials.isPresent()){
            this.spotifyApi = spotifyApi(Optional.of(credentials.get().accessToken), Optional.of(credentials.get().refreshToken));
        } else {
            throw new NoSPApiException("asdlfsdf");
        }


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




    /*private SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(Optional.of(accessToken))
            .build();*/

    /*private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();*/


}
