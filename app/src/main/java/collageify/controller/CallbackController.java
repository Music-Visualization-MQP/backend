package collageify.controller;

import collageify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;

@RestController
@RequestMapping("/callback")
public class CallbackController {
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/callback/get-refresh-token/");
    private String code = "";
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(System.getenv("SP_CID"))
            .setClientSecret("SP_S")
            .setRedirectUri(redirectUri)
            .build();
    @Autowired
    private UserRepository usrRepo;

    @GetMapping("/loading")
    @ResponseBody
    public String spotifyLogin() {
        AuthorizationCodeUriRequest spotifyAuthCodeRequest = spotifyApi.authorizationCodeUri()
                .scope("user-read-playback-state, user-read-currently-playing")
                .show_dialog(true)
                .build();
        final URI uri = spotifyAuthCodeRequest.execute();
        return uri.toString();

    }




}
