package collageify.collageify.controller;

import collageify.collageify.entities.SpotifyClientCredentials;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.Optional;

public interface ISpotifyCredentialManagementStrategy {
    public Optional<SpotifyClientCredentials> getNewAccessToken(SpotifyClientCredentials credentials) throws IOException, SpotifyWebApiException;

}
