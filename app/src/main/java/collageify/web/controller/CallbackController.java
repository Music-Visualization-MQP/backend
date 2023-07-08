package collageify.web.controller;

import collageify.collageify.db.SQLAccess;
import collageify.web.exceptions.NoSPApiException;
import collageify.web.repository.RoleRepository;
import collageify.web.repository.UserRepository;
import collageify.web.service.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/callback")
public class CallbackController {
    @Autowired
    private AuthenticationManager authMgr;

    @Autowired
    private UserRepository usrRepo;


    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtService jwtService;
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:4200/callback");
    private String code = "";
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(System.getenv("SP_CID"))
            .setClientSecret(System.getenv("SP_S"))
            .setRedirectUri(redirectUri)
            .build();

    private SQLAccess sql = new SQLAccess();

    /**
     * This function does not consume anything and is a resd endpoint for the client
     *
     * @return produces a uri that allows a user to grant collageify access to their data
     */
    @GetMapping("/loading")
    public ResponseEntity<String> spotifyLogin(@RequestHeader("Authorization") String authHeader) throws SQLException, NoSPApiException {
        String token;
        String username;
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
            if(sql.checkIfCredsExistByEmail(username)) {
                return new ResponseEntity<>("http://localhost:4200", HttpStatus.OK);
            } else {
                AuthorizationCodeUriRequest spotifyAuthCodeRequest = spotifyApi.authorizationCodeUri()
                        .scope("user-read-playback-state, user-read-currently-playing")
                        .show_dialog(true)
                        .build();
                final URI uri = spotifyAuthCodeRequest.execute();
                return new ResponseEntity<>(uri.toString(), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }


    }
    @GetMapping("/spotify-authorized")
    public String getUserDetails(){
        return "here's your data!";
    }
    @PostMapping("/set-refresh-token")
    public void getSpotifyRefreshToken(@RequestHeader("Authorization") String authHeader, @RequestBody JsonNode requestBody, HttpServletResponse response) throws IOException, SQLException {
        String username = null;
        Optional<String> authHeaderDecode = getUsername(authHeader);
        String code = requestBody.get("code").asText();
        if(authHeaderDecode.isPresent()) {
            username = authHeaderDecode.get();
            AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
            try {
                final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();


                long millis = System.currentTimeMillis();
                System.out.println("expires in:" + authorizationCodeCredentials.getExpiresIn());
            /*
              the code below this is terrible
              and  needs to be (refactored?) whatever
              just make this a private class that takes the arguments
              and gives them to the playing side of the server
             */

                this.sql.estConnection();
                this.sql.addSpotifyCredentials(username,
                        authorizationCodeCredentials.getAccessToken(),
                        authorizationCodeCredentials.getRefreshToken(),
                        millis + (authorizationCodeCredentials.getExpiresIn() * 1000));
                response.sendRedirect("http://localhost:4200");

            } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException | SQLException e) {
                System.out.println("error:" + e.getMessage());
            }
        }

    }
    private Optional<String> getUsername(String authHeader){
        String username;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(jwtService.extractUsername(authHeader.substring(7)));
        } else {
            return Optional.empty();
        }
    }







}
