package collageify.exceptions;

public class NoSPApiException extends Throwable {
    public NoSPApiException(String invalidSpotifyApi) {
        super(invalidSpotifyApi);
    }
}
