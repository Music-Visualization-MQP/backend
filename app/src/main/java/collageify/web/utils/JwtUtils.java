package collageify.web.utils;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JwtUtils {
    private static final String SECRET = System.getenv("MY_SECRET");

    public static SecretKey getSignKey() {
        // Generate a secret key for HMAC-SHA256
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

}



