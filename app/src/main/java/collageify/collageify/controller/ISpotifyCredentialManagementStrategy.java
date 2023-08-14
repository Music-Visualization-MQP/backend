package collageify.collageify.controller;

import collageify.collageify.entities.SpotifyUserCredentials;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.Optional;

public interface ISpotifyCredentialManagementStrategy {
    public Optional<SpotifyUserCredentials> getNewAccessToken(SpotifyUserCredentials credentials) throws IOException, SpotifyWebApiException;

}
