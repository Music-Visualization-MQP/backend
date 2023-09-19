package collageify.collageify.entities;

public interface SpotifyClientCredentialState {
    boolean isValid();
    SpotifyCredential  next();
    }
}
