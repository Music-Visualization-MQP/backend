package collageify.collageify.controller;

import collageify.collageify.entities.SpotifyUserCredentials;
import collageify.web.exceptions.NoSPApiException;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpotifyCredentialsController {
    private SqlController sql = new SqlController();
    public Map<Integer, SpotifyUserCredentials> credentialsMap = Collections.synchronizedMap(new HashMap<>());
    public Map<Integer, SpotifyUserCredentials> expiredCredsMap = Collections.synchronizedMap(new HashMap<>());

    public SpotifyCredentialsController() throws SQLException, NoSPApiException {
        credentialsMap = sql.getAuthCredentials();
    }

    private
}
